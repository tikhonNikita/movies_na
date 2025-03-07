package com.movieslist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movieslist.data.FavoriteMovieRepository
import com.movieslist.data.MovieRepository
import com.movieslist.data.local.MoviesDatabase
import com.movieslist.domain.model.Movie
import com.movieslist.ui.compose.MoviesScreenState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviesListViewModel(context: Context) : ViewModel() {

    private val movieRepository: MovieRepository
    private val favoriteRepository: FavoriteMovieRepository

    private var cachedMovies: List<Movie>? = null
    private val _favoriteMovieIds = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteMovieIds: StateFlow<Set<Int>> = _favoriteMovieIds.asStateFlow()


    private val _uiState = MutableStateFlow<MoviesScreenState>(MoviesScreenState.Empty())
    val uiState: StateFlow<MoviesScreenState> = _uiState.asStateFlow()

    private val _paginate = MutableSharedFlow<Unit>()
    val paginate: SharedFlow<Unit> = _paginate.asSharedFlow()

    var onEndReachedCallback: (() -> Unit)? = null

    private var loadMoreJob: Job? = null
    private val debounceInterval = 1000L


    init {
        val database = MoviesDatabase.getDatabase(context)
        movieRepository = MovieRepository(database.movieDao())
        favoriteRepository = FavoriteMovieRepository(database.favoriteMovieDao())

        viewModelScope.launch {
            cachedMovies = movieRepository.getAllMoviesList()
            favoriteRepository.getAllFavoriteMovies().collect { favoriteMovies ->
                _favoriteMovieIds.value = favoriteMovies.map { it.id }.toSet()
            }
        }
    }


    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            val currentIds = _favoriteMovieIds.value
            val isCurrentlyFavorite = currentIds.contains(movie.id)

            if (isCurrentlyFavorite) {
                favoriteRepository.removeMovieFromFavorites(movie.id)
                _favoriteMovieIds.value = currentIds - movie.id
            } else {
                favoriteRepository.addMovieToFavorites(movie)
                _favoriteMovieIds.value = currentIds + movie.id
            }
        }
    }

    fun loadMoreData() {
        loadMoreJob?.cancel()

        val hasMore = when (val state = _uiState.value) {
            is MoviesScreenState.Success -> state.hasMore
            is MoviesScreenState.SuccessMore -> state.hasMore
            is MoviesScreenState.LoadingMore -> state.hasMore
            else -> false
        }

        if (!hasMore) return

        loadMoreJob = viewModelScope.launch {
            delay(debounceInterval)
            onEndReachedCallback?.invoke()
        }
    }

    fun onPaginateRequested() {
        viewModelScope.launch {
            _paginate.emit(Unit)
        }
    }

    fun setState(state: MoviesScreenState) {
        viewModelScope.launch {
            when (state) {
                is MoviesScreenState.Success -> {
                    movieRepository.saveMovies(state.movies)
                    cachedMovies = state.movies
                    _uiState.value = state
                }

                is MoviesScreenState.SuccessMore -> {
                    _uiState.value = state
                }

                is MoviesScreenState.Empty -> {
                    if (cachedMovies.isNullOrEmpty()) {
                        _uiState.value = state
                    } else {
                        _uiState.value = MoviesScreenState.Empty(cachedMovies)
                    }
                }

                is MoviesScreenState.Loading -> {
                    if (cachedMovies.isNullOrEmpty()) {
                        _uiState.value = state
                    } else {
                        _uiState.value = MoviesScreenState.Loading(cachedMovies)
                    }
                }

                is MoviesScreenState.Error -> {
                    if (cachedMovies.isNullOrEmpty()) {
                        _uiState.value = state
                    } else {
                        _uiState.value = MoviesScreenState.Error(state.message, cachedMovies)
                    }
                }

                else -> _uiState.value = state
            }
        }
    }
}