package com.movieslist.util

import com.movieslist.domain.model.Movie

fun Map<*, *>.toMovie(): Movie? {
    val id = (this["id"] as? Double)?.toInt() ?: return null
    val title = this["title"] as? String ?: return null
    val url = this["url"] as? String ?: return null
    val movieDescription = this["movieDescription"] as? String ?: return null
    val rating = this["rating"] as? Double ?: return null

    return Movie(id, url, title, movieDescription, rating)
}
