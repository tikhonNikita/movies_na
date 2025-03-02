package com.movieslist

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableMap
import com.facebook.react.uimanager.UIManagerHelper
import com.facebook.react.uimanager.events.Event
import com.movieslist.domain.model.Movie
import com.movieslist.ui.compose.MoviesListRootComposeView
import com.movieslist.ui.compose.MoviesScreenState
import com.movieslist.ui.compose.NativeMovieState
import com.movieslist.util.getBooleanOrDefault
import com.movieslist.util.toMovie

private class MoreMoviesRequestedEvent(
    surfaceId: Int,
    viewId: Int
) : Event<MoreMoviesRequestedEvent>(surfaceId, viewId) {
    override fun getEventName(): String {
        return "onMoreMoviesRequested"
    }

    override fun getEventData(): WritableMap? {
        return null
    }
}


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
            vm.onEndReachedCallback = ::onReachEndOfList
            addView(
                ComposeView(context).apply {
                    setViewCompositionStrategy(
                        ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
                    )

                    setContent { MoviesListRootComposeView(vm) }
                },
                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            )
        }
    }


    private fun getCurrentMovies(): List<Movie> {
        return when (val currentState = viewModel?.uiState?.value) {
            is MoviesScreenState.Success -> currentState.movies
            is MoviesScreenState.SuccessMore -> currentState.movies
            is MoviesScreenState.LoadingMore -> currentState.movies
            else -> emptyList()
        }
    }

    fun setMoviesStateFromJS(value: ReadableMap) {
        val state = NativeMovieState.fromString(value.getString("state"))
        val data = value.getArray("data")
        val canLoadMore = value.getBooleanOrDefault("canLoadMore", false)
        val message = value.getString("message")

        val moviesScreenState = when (state) {
            NativeMovieState.EMPTY -> MoviesScreenState.Empty()
            NativeMovieState.LOADING -> MoviesScreenState.Loading()

            NativeMovieState.SUCCESS, NativeMovieState.SUCCESS_MORE -> {
                val movies =
                    data?.toArrayList()?.mapNotNull { (it as? Map<*, *>)?.toMovie() } ?: emptyList()
                if (state == NativeMovieState.SUCCESS) {
                    MoviesScreenState.Success(movies, canLoadMore)
                } else {
                    MoviesScreenState.SuccessMore(
                        (getCurrentMovies() + movies).distinctBy { it.id },
                        canLoadMore
                    )
                }
            }

            NativeMovieState.ERROR -> MoviesScreenState.Error(
                message ?: "An unknown error occurred"
            )

            NativeMovieState.LOADING_MORE -> MoviesScreenState.LoadingMore(
                getCurrentMovies(),
                canLoadMore
            )
        }

        viewModel?.setState(moviesScreenState)
    }

    fun onReachEndOfList() {
        val reactContext = context as? ReactContext ?: return
        val surfaceId = UIManagerHelper.getSurfaceId(reactContext)
        val eventDispatcher = UIManagerHelper.getEventDispatcherForReactTag(reactContext, id)
        eventDispatcher?.dispatchEvent(MoreMoviesRequestedEvent(surfaceId, id))
    }
}