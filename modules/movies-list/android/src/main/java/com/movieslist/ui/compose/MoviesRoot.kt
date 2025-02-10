package com.movieslist.ui.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.movieslist.MoviesListViewModel
import com.movieslist.domain.model.Movie
import com.movieslist.ui.compose.theme.MoviesListTheme
import androidx.compose.runtime.collectAsState

@Composable
fun MoviesListRootComposeView(viewModel: MoviesListViewModel) {

    val uiState by viewModel.uiState.collectAsState()

    MoviesListTheme {
        Surface {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (uiState) {
                    is MoviesScreenState.Empty -> Text("No movies available")
                    is MoviesScreenState.Loading, is MoviesScreenState.LoadingMore -> CircularProgressIndicator()
                    is MoviesScreenState.Success, is MoviesScreenState.SuccessMore -> {
                        MoviesGrid(movies = uiState.movies, viewModel.lazyGridState)
                    }
                    is MoviesScreenState.Error -> Text(uiState.message)
                }
            }
        }
    }
}


private val MoviesScreenState.movies: List<Movie>
    get() = when (this) {
        is MoviesScreenState.Success -> this.movies
        is MoviesScreenState.SuccessMore -> this.movies
        else -> emptyList()
    }

private val MoviesScreenState.message: String
    get() = when (this) {
        is MoviesScreenState.Error -> this.message
        else -> ""
    }