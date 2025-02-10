package com.movieslist.ui.compose

import com.movieslist.domain.model.Movie

sealed class MoviesScreenState {
    object Empty : MoviesScreenState()
    object Loading : MoviesScreenState()
    data class Success(val movies: List<Movie>, val hasMore: Boolean) : MoviesScreenState()
    data class Error(val message: String) : MoviesScreenState()
    data class LoadingMore(val movies: List<Movie>, val hasMore: Boolean) : MoviesScreenState()
    data class SuccessMore(val movies: List<Movie>, val hasMore: Boolean) : MoviesScreenState()
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