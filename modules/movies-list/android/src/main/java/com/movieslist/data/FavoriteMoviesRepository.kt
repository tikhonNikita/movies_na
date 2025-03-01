package com.movieslist.data

import com.movieslist.data.local.FavoriteMovieDao
import com.movieslist.data.local.toDomainModel
import com.movieslist.data.local.toFavoriteEntity
import com.movieslist.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteMovieRepository(private val favoriteMovieDao: FavoriteMovieDao) {

    fun getAllFavoriteMovies(): Flow<List<Movie>> {
        return favoriteMovieDao.getAllFavoriteMovies().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    suspend fun getAllFavoriteMoviesList(): List<Movie> {
        return favoriteMovieDao.getAllFavoriteMoviesList().map { it.toDomainModel() }
    }

    suspend fun addMovieToFavorites(movie: Movie): Long {
        return favoriteMovieDao.insertFavoriteMovie(movie.toFavoriteEntity())
    }

    suspend fun isMovieFavorite(movieId: Int): Boolean {
        return favoriteMovieDao.isMovieFavorite(movieId)
    }

    suspend fun removeMovieFromFavorites(movieId: Int): Int {
        return favoriteMovieDao.removeFavoriteMovie(movieId)
    }
}