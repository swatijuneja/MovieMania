package com.example.movies.models

data class MovieModel(
    var page: Int,
    var results: MutableList<MovieItemModel>,
    var total_pages: Int,
    var total_results: Int
)