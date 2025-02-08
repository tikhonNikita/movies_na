package com.movieslist

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.compose.ui.platform.ComposeView
import com.facebook.react.bridge.ReadableMap
import com.movieslist.domain.model.Movie
import com.movieslist.ui.compose.MoviesListRootComposeView
import com.movieslist.ui.compose.MoviesScreenState
import com.movieslist.ui.compose.NativeMovieState
import com.movieslist.util.getBooleanOrDefault
import com.movieslist.util.toMovie


class MoviesListView : FrameLayout {
    private var viewModel: MoviesListViewModel? = null

    constructor(context: Context, viewModel: MoviesListViewModel) : super(context) {
        this.viewModel = viewModel
        setupComposeView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        setupComposeView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context!!, attrs, defStyleAttr) {
        setupComposeView()
    }

    private fun setupComposeView() {
        viewModel?.let { vm ->
            val composeView = ComposeView(context).apply {
                setContent {
                    MoviesListRootComposeView(vm)
                }
            }
            addView(composeView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        }
    }

    fun setMoviesStateFromJS(value: ReadableMap) {
        val state = NativeMovieState.fromString(value.getString("state"))
        val data = value.getArray("data")
        val canLoadMore = value.getBooleanOrDefault("canLoadMore", false)
        val message = value.getString("message")

        val moviesScreenState = when (state) {
            NativeMovieState.EMPTY -> MoviesScreenState.Empty
            NativeMovieState.LOADING -> MoviesScreenState.Loading

            NativeMovieState.SUCCESS, NativeMovieState.SUCCESS_MORE -> {
                val movies = data?.toArrayList()?.mapNotNull {
                    (it as? Map<*, *>)?.toMovie()
                } ?: emptyList()

                if (state == NativeMovieState.SUCCESS) {
                    MoviesScreenState.Success(movies, canLoadMore)
                } else {
                    val currentMovies: List<Movie> =
                        when (val currentState = viewModel?.uiState?.value) {
                            is MoviesScreenState.Success -> currentState.movies
                            is MoviesScreenState.SuccessMore -> currentState.movies
                            else -> emptyList()
                        }
                    MoviesScreenState.SuccessMore(currentMovies + movies, canLoadMore)
                }
            }

            NativeMovieState.ERROR -> MoviesScreenState.Error(
                message ?: "An unknown error occurred"
            )

            NativeMovieState.LOADING_MORE -> MoviesScreenState.LoadingMore
        }

        viewModel?.setState(moviesScreenState)
    }
}