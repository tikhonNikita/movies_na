package com.movieslist

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableArray
import com.facebook.react.bridge.Arguments
import com.movieslist.data.FavoriteMovieRepository
import com.movieslist.data.local.MoviesDatabase
import com.movieslist.domain.model.Movie
import com.movieslist.util.toMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteMoviesModule(reactContext: ReactApplicationContext) :
    NativeFavoriteMoviesModuleSpec(reactContext) {

    private val repository: FavoriteMovieRepository
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        val database = MoviesDatabase.getDatabase(reactContext)
        repository = FavoriteMovieRepository(database.favoriteMovieDao())
    }


    override fun addMovieToFavorites(movie: ReadableMap, promise: Promise) {
        val movieObj = (movie.toHashMap() as? Map<*, *>)?.toMovie()

        if (movieObj == null) {
            promise.reject("ERROR", "Invalid movie data provided")
            return
        }

        coroutineScope.launch {
            try {
                val result = repository.addMovieToFavorites(movieObj)
                promise.resolve(result.toDouble())
            } catch (e: Exception) {
                promise.reject("ERROR", e.message ?: "Unknown error occurred")
            }
        }
    }


    override fun removeMovieFromFavorites(movieId: Double, promise: Promise) {
        coroutineScope.launch {
            try {
                val result = repository.removeMovieFromFavorites(movieId.toInt())
                promise.resolve(result > 0)
            } catch (e: Exception) {
                promise.reject("ERROR", e.message ?: "Unknown error occurred")
            }
        }
    }

    override fun isMovieFavorite(movieId: Double, promise: Promise) {
        coroutineScope.launch {
            try {
                val result = repository.isMovieFavorite(movieId.toInt())
                promise.resolve(result)
            } catch (e: Exception) {
                promise.reject("ERROR", e.message ?: "Unknown error occurred")
            }
        }
    }

    override fun getFavoriteMovies(promise: Promise) {
        coroutineScope.launch {
            try {
                val movies = repository.getAllFavoriteMoviesList()
                val array = convertMoviesToJsArray(movies)

                promise.resolve(array)

            } catch (e: Exception) {
                promise.reject("ERROR", e.message ?: "Unknown error occurred")
            }
        }
    }

    private fun convertMoviesToJsArray(movies: List<Movie>): WritableArray {
        val array = Arguments.createArray()

        movies.forEach { movie ->
            val map = Arguments.createMap()
            map.putInt("id", movie.id)
            map.putString("url", movie.url)
            map.putString("title", movie.title)
            map.putString("movieDescription", movie.movieDescription)
            map.putDouble("rating", movie.rating)
            array.pushMap(map)
        }

        return array
    }
}