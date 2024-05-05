package com.example.movies.data.retrofit

class MovieApiClient {

    val movieInterface = RetrofitBuilder.builder
        .create(MovieInterface::class.java)
}