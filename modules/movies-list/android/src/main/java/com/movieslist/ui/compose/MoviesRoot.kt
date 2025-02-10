package com.movieslist.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.movieslist.MoviesListViewModel
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
                when (val state = uiState) {
                    is MoviesScreenState.Empty -> Text("No movies available")
                    is MoviesScreenState.Loading -> CircularProgressIndicator()
                    is MoviesScreenState.Error -> Text(state.message)

                    is MoviesScreenState.HasMovies -> {
                        MoviesGrid(movies = state.movies, gridState = viewModel.lazyGridState)
                    }
                }
            }
        }
    }
}
