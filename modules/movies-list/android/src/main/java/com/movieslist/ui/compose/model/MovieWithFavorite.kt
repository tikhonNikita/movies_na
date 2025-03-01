package com.movieslist.ui.compose.model

import com.movieslist.domain.model.Movie


data class MovieWithFavorite(
    val id: Int,
    val url: String,
    val title: String,
    val movieDescription: String,
    val rating: Double,
    val isFavorite: Boolean
)

fun MovieWithFavorite.toMovie(): Movie {
    return Movie(
        id = id,
        url = url,
        title = title,
        movieDescription = movieDescription,
        rating = rating
    )
}

fun Movie.toUiModel(isFavorite: Boolean): MovieWithFavorite {
    return MovieWithFavorite(
        id = id,
        url = url,
        title = title,
        movieDescription = movieDescription,
        rating = rating,
        isFavorite = isFavorite
    )
}
