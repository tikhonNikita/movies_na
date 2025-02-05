package com.movieslist.ui.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.movieslist.MoviesListViewModel
import com.movieslist.ui.compose.theme.MoviesListTheme

@Composable
fun MoviesListRootComposeView(viewModel: MoviesListViewModel) {
    MoviesListTheme(isSystemInDarkTheme()) {
        Surface {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                MoviesGrid()
            }
        }
    }
}