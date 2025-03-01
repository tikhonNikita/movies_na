package com.movieslist.ui.compose

import ErrorHandler
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.movieslist.R
import com.movieslist.domain.model.FakeMovies
import com.movieslist.domain.model.Movie
import com.movieslist.ui.compose.model.MovieWithFavorite
import com.movieslist.ui.compose.model.toMovie
import com.movieslist.ui.compose.model.toUiModel
import com.movieslist.ui.compose.theme.Dimens
import com.movieslist.ui.compose.theme.MoviesListTheme
import androidx.compose.material3.TooltipBox
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Composable
fun MovieGridItem(
    movie: MovieWithFavorite,
    toggleFavorite: (Movie) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(Dimens.cardPadding)
            .fillMaxWidth()
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.gridImageHeight)
            ) {
                AsyncImage(model = movie.url,
                    contentDescription = movie.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.image_placeholder),
                    error = painterResource(id = R.drawable.image_error),
                    onError = { result ->
                        Log.e("Coil", "Error loading image: ${result.result}")
                    })
                Icon(
                    imageVector = if (movie.isFavorite) Icons.Filled.Favorite else Icons.Filled.Favorite,
                    contentDescription = "Favorite",
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .clickable { toggleFavorite(movie.toMovie()) },
                    tint = if (movie.isFavorite) Color.Red else Color.Gray
                )
            }
            Column(
                modifier = Modifier.padding(Dimens.contentPadding)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = Dimens.cardPadding),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                )
                Text(
                    text = movie.movieDescription,
                    modifier = Modifier.heightIn(
                        max = Dimens.textMaxHeight,
                        min = Dimens.textMinHeight
                    ),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun MoviesGrid(
    errorMessage: String? = null,
    movies: List<Movie>,
    favoriteMovieIds: Set<Int>,
    gridState: LazyGridState,
    toggleFavorite: (Movie) -> Unit
) {
    val moviesWithFavorites = remember(movies, favoriteMovieIds) {
        derivedStateOf {
            movies.map { movie ->
                movie.toUiModel(isFavorite = favoriteMovieIds.contains(movie.id))
            }
        }
    }

    ErrorHandler(errorMessage = errorMessage)
    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Adaptive(minSize = Dimens.gridColumnsMinSize),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(Dimens.cardPadding),
    ) {
        items(moviesWithFavorites.value, key = { it.id }) { movie ->
            MovieGridItem(
                movie = movie,
                toggleFavorite = toggleFavorite
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun PreviewMoviesGrid() {
    MoviesListTheme {
        Surface {
            MoviesGrid(
                movies = FakeMovies.movies,
                errorMessage = null,
                favoriteMovieIds = setOf(1, 3),
                gridState = LazyGridState(),
                toggleFavorite = {}
            )
        }
    }
}