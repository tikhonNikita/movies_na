package com.movieslist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MoviesListViewModel : ViewModel() {
    private var _title by mutableStateOf("Hello World")

   val title get() = _title

    fun setTitle(newTitle: String) {
        _title = newTitle
    }
}