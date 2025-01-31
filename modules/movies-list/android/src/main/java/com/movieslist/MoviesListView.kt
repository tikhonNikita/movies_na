package com.movieslist

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView

class MoviesListView : FrameLayout {
  constructor(context: Context?) : super(context!!) {
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
    val composeView = ComposeView(context).apply {
      setContent {
        MoviesListViewContent()
      }
    }
    this.layoutParams = ViewGroup.LayoutParams(
      LayoutParams.MATCH_PARENT,
      LayoutParams.MATCH_PARENT
    )
    addView(
      composeView,
      LayoutParams(
        LayoutParams.MATCH_PARENT,
        LayoutParams.MATCH_PARENT
      )
    )
  }
}

@Composable
fun MoviesListViewContent() {
  MaterialTheme {
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      Text(text = "Hello World")
    }
  }
}