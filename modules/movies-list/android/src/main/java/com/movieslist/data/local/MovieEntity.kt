package com.movieslist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.movieslist.domain.model.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val url: String,
    val title: String,
    val movieDescription: String,
    val rating: Double
)

fun MovieEntity.toDomainModel(): Movie {
    return Movie(
        id = id,
        url = url,
        title = title,
        movieDescription = movieDescription,
        rating = rating
    )
}

fun Movie.toEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        url = url,
        title = title,
        movieDescription = movieDescription,
        rating = rating
    )
}