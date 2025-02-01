package com.movieslist

import android.graphics.Color
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewManagerDelegate
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.viewmanagers.MoviesListViewManagerInterface
import com.facebook.react.viewmanagers.MoviesListViewManagerDelegate

@ReactModule(name = MoviesListViewManager.NAME)
class MoviesListViewManager : SimpleViewManager<MoviesListView>(),
    MoviesListViewManagerInterface<MoviesListView> {
    private val mDelegate: ViewManagerDelegate<MoviesListView>

    init {
        mDelegate = MoviesListViewManagerDelegate(this)
    }

    override fun getDelegate(): ViewManagerDelegate<MoviesListView>? {
        return mDelegate
    }

    override fun getName(): String {
        return NAME
    }

    public override fun createViewInstance(context: ThemedReactContext): MoviesListView {
        val moviesListViewModel = MoviesListViewModel()
        return MoviesListView(context, moviesListViewModel)
    }


    @ReactProp(name = "color")
    override fun setColor(view: MoviesListView?, color: String?) {
        view?.setBackgroundColor(Color.parseColor(color))
    }

    @ReactProp(name = "title")
    override fun setTitle(view: MoviesListView?, title: String?) {
        title?.let { view?.setTitle(it) }
    }


    companion object {
        const val NAME = "MoviesListView"
    }
}
