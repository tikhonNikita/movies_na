package com.movieslist

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.compose.ui.platform.ComposeView
import com.movieslist.ui.compose.MoviesListRootComposeView

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
        viewModel?.let {
            val composeView = ComposeView(context).apply {
                setContent {
                    MoviesListRootComposeView(it)
                }
            }
            addView(
                composeView,
                LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT
                )
            )
        }
    }

    fun setTitle(title: String) {
        viewModel?.setTitle(title)
    }
}
