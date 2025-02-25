package com.movieslist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movieslist.data.MovieRepository
import com.movieslist.data.local.MoviesDatabase
import com.movieslist.ui.compose.MoviesScreenState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MoviesListViewModel(context: Context) : ViewModel() {

    private val repository: MovieRepository

    init {
        val movieDao = MoviesDatabase.getDatabase(context).movieDao()
        repository = MovieRepository(movieDao)

        viewModelScope.launch {
            repository.getAllMovies().collectLatest { movies ->
                if (movies.isNotEmpty() && _uiState.value is MoviesScreenState.Loading) {
                    _uiState.value = MoviesScreenState.Success(movies, true)
                }
            }
        }
    }

    private val _uiState = MutableStateFlow<MoviesScreenState>(MoviesScreenState.Empty)
    val uiState: StateFlow<MoviesScreenState> = _uiState.asStateFlow()

    private val _paginate = MutableSharedFlow<Unit>()
    val paginate: SharedFlow<Unit> = _paginate.asSharedFlow()

    var onEndReachedCallback: (() -> Unit)? = null

    private var loadMoreJob: Job? = null
    private val debounceInterval = 1000L

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
                    repository.saveMovies(state.movies)
                    _uiState.value = state
                }
                is MoviesScreenState.SuccessMore -> {
                    repository.saveMovies(state.movies)
                    _uiState.value = state
                }
                else -> _uiState.value = state
            }
        }
    }

    // Load cached data
    fun loadCachedData() {
        viewModelScope.launch {
            val cachedMovies = repository.getAllMoviesList()
            if (cachedMovies.isNotEmpty()) {
                _uiState.value = MoviesScreenState.Success(cachedMovies, true)
            } else {
                _uiState.value = MoviesScreenState.Loading
            }
        }
    }
}