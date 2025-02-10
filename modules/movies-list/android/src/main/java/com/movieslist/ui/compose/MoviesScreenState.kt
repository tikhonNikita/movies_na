package com.movieslist.ui.compose

import com.movieslist.domain.model.Movie

sealed class MoviesScreenState {
    object Empty : MoviesScreenState()
    object Loading : MoviesScreenState()
    data class Error(val message: String) : MoviesScreenState()

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