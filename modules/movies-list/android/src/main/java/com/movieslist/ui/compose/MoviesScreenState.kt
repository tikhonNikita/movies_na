package com.movieslist.ui.compose

import com.movieslist.domain.model.Movie

sealed class MoviesScreenState {
    data class Empty(val movies: List<Movie>? = null) : MoviesScreenState()
    data class Loading(val movies: List<Movie>? = null) : MoviesScreenState()
    data class Error(val message: String, val movies: List<Movie>? = null) : MoviesScreenState()

    sealed interface HasMovies {
        val movies: List<Movie>
    }

    data class Success(override val movies: List<Movie>, val hasMore: Boolean) :
        MoviesScreenState(),
        HasMovies

    data class LoadingMore(override val movies: List<Movie>, val hasMore: Boolean) :
        MoviesScreenState(),
        HasMovies

    data class SuccessMore(override val movies: List<Movie>, val hasMore: Boolean) :
        MoviesScreenState(),
        HasMovies
}

enum class NativeMovieState {
    EMPTY,
    LOADING,
    SUCCESS,
    ERROR,
    LOADING_MORE,
    SUCCESS_MORE;

    companion object {
        fun fromString(value: String?): NativeMovieState {
            return try {
                valueOf(value?.uppercase() ?: "EMPTY")
            } catch (e: IllegalArgumentException) {
                EMPTY
            }
        }
    }
}