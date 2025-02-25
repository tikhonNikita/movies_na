package com.movieslist.data

import com.movieslist.data.local.MovieDao
import com.movieslist.data.local.toDomainModel
import com.movieslist.data.local.toEntity
import com.movieslist.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(private val movieDao: MovieDao) {

    fun getAllMovies(): Flow<List<Movie>> {
        return movieDao.getAllMovies().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    suspend fun getAllMoviesList(): List<Movie> {
        return movieDao.getAllMoviesList().map { it.toDomainModel() }
    }

    suspend fun saveMovies(movies: List<Movie>) {
        movieDao.insertMovies(movies.map { it.toEntity() })
    }

    suspend fun clearMovies() {
        movieDao.clearAllMovies()
    }
}