package com.movieslist.util

import com.movieslist.domain.model.Movie

fun Map<*, *>.toMovie(): Movie? {
    val id = (this["id"] as? Double)?.toInt()
    val title = this["title"] as? String
    val url = this["url"] as? String
    val movieDescription = this["movieDescription"] as? String
    val rating = this["rating"] as? Double

    if (id == null || title == null || url == null || movieDescription == null || rating == null) {
        return null
    }

    return Movie(id, url, title, movieDescription, rating)
}