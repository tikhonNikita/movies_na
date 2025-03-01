package com.movieslist.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.movieslist.MoviesListViewModel
import com.movieslist.ui.compose.theme.MoviesListTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import com.movieslist.domain.model.Movie

@Composable
fun MoviesListRootComposeView(viewModel: MoviesListViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val favoriteMovieIds by viewModel.favoriteMovieIds.collectAsState()
    val lazyGridState = rememberLazyGridState()

    HandlePagination(viewModel, lazyGridState)

    MoviesListTheme {
        Surface {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                MoviesScreenContent(uiState, favoriteMovieIds, lazyGridState, viewModel::toggleFavorite)
            }
        }
    }
}

@Composable
private fun HandlePagination(viewModel: MoviesListViewModel, lazyGridState: LazyGridState) {
    LaunchedEffect(viewModel.paginate) {
        viewModel.paginate.collect {
            viewModel.loadMoreData()
        }
    }

    LaunchedEffect(lazyGridState) {
        snapshotFlow { lazyGridState.layoutInfo }
            .collect { layoutInfo ->
                val totalItems = layoutInfo.totalItemsCount
                val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

                if (totalItems > 0 && lastVisibleIndex >= totalItems - 1) {
                    viewModel.onPaginateRequested()
                }
            }
    }
}

@Composable
private fun MoviesScreenContent(
    uiState: MoviesScreenState,
    favoriteMovieIds: Set<Int>,
    lazyGridState: LazyGridState,
    toggleFavorite: (Movie) -> Unit
) {
    when (uiState) {
        is MoviesScreenState.Loading -> {
            if (uiState.movies.isNullOrEmpty()) {
                CircularProgressIndicator()
            } else {
                MoviesGrid(
                    movies = uiState.movies,
                    gridState = lazyGridState,
                    toggleFavorite = toggleFavorite,
                    favoriteMovieIds = favoriteMovieIds
                )
            }
        }

        is MoviesScreenState.Error -> {
            if (uiState.movies.isNullOrEmpty()) {
                Text(uiState.message)
            } else {
                MoviesGrid(
                    movies = uiState.movies,
                    gridState = lazyGridState,
                    toggleFavorite = toggleFavorite,
                    favoriteMovieIds = favoriteMovieIds,
                    errorMessage = uiState.message
                )
            }
        }

        is MoviesScreenState.Empty -> {
            if (uiState.movies.isNullOrEmpty()) {
                Text("No movies found")
            } else {
                MoviesGrid(
                    movies = uiState.movies,
                    gridState = lazyGridState,
                    toggleFavorite = toggleFavorite,
                    favoriteMovieIds = favoriteMovieIds
                )
            }
        }

        is MoviesScreenState.HasMovies -> {
            MoviesGrid(
                movies = uiState.movies,
                gridState = lazyGridState,
                toggleFavorite = toggleFavorite,
                favoriteMovieIds = favoriteMovieIds
            )
        }
    }
}