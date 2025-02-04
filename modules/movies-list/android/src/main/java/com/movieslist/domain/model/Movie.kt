package com.movieslist.domain.model

data class Movie(
    val id: Int,
    val url: String,
    val title: String,
    val movieDescription: String,
    val rating: Double
)

object FakeMovies {
    val movies = listOf(
        Movie(
            id = 1,
            url = "https://fastly.picsum.photos/id/228/200/200.jpg?hmac=o6k6dSrgAeHp1V6rxIjRR2cwEeu4DUs9Z1-sLxrQ878",
            title = "Android",
            movieDescription = "Android is a mobile operating system based on a modified version of the Linux kernel and other open source software, designed primarily for touchscreen mobile devices such as smartphones and tablets.",
            rating = 4.5
        ),
        Movie(
            id = 2,
            url = "https://fastly.picsum.photos/id/417/200/200.jpg?hmac=urRppSmoZMSijmMMM_igfBcmbcTu_y285erBFfY7jE4",
            title = "Android",
            movieDescription = "Android is a mobile operating system based on a modified version of the Linux kernel and other open source software, designed primarily for touchscreen mobile devices such as smartphones and tablets.",
            rating = 4.5
        ),
        Movie(
            id = 3,
            url = "https://fastly.picsum.photos/id/703/200/200.jpg?hmac=6zWxIBRmIf2e0jZTqvKBIwrc7wm-dPkvGky4go6Yyvg",
            title = "Android",
            movieDescription = "Android is a mobile operating system based on a modified version of the Linux kernel and other open source software, designed primarily for touchscreen mobile devices such as smartphones and tablets.",
            rating = 4.5
        ),
        Movie(
            id = 4,
            url = "https://picsum.photos/200",
            title = "Android",
            movieDescription = "Android is a mobile operating system based on a modified version of the Linux kernel and other open source software, designed primarily for touchscreen mobile devices such as smartphones and tablets.",
            rating = 4.5
        ),
        Movie(
            id = 5,
            url = "https://picsum.photos/200",
            title = "Android",
            movieDescription = "Android is a mobile operating system based on a modified version of the Linux kernel and other open source software, designed primarily for touchscreen mobile devices such as smartphones and tablets.",
            rating = 4.5
        ),
        Movie(
            id = 6,
            url = "https://picsum.photos/200",
            title = "Android",
            movieDescription = "Android is a mobile operating system based on a modified version of the Linux kernel and other open source software, designed primarily for touchscreen mobile devices such as smartphones and tablets.",
            rating = 4.5
        )
    )
}