package com.movieslist

import androidx.lifecycle.ViewModel
import com.movieslist.ui.compose.MoviesScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MoviesListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<MoviesScreenState>(MoviesScreenState.Empty)
    val uiState: StateFlow<MoviesScreenState> = _uiState.asStateFlow()

    fun setState(state: MoviesScreenState) {
        _uiState.value = state
    }
}