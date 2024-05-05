package com.example.movies.models

data class FavoriteMovie(
    var isFavorite: Boolean = false,
    var movieItem: MovieItemModel
)