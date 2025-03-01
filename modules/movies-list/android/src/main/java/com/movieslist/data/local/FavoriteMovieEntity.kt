package com.movieslist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.movieslist.domain.model.Movie

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey val id: Int,
    val url: String,
    val title: String,
    val movieDescription: String,
    val rating: Double,
    val addedAt: Long = System.currentTimeMillis()
)

fun FavoriteMovieEntity.toDomainModel(): Movie {
    return Movie(
        id = id,
        url = url,
        title = title,
        movieDescription = movieDescription,
        rating = rating
    )
}

fun Movie.toFavoriteEntity(): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        id = id,
        url = url,
        title = title,
        movieDescription = movieDescription,
        rating = rating
    )
}