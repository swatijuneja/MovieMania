package com.example.movies.data.retrofit

import com.example.movies.models.MovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface MovieInterface {

    @GET("/3/movie/top_rated?language=en-US&page=1")
    suspend fun getTopRatedMovieList(@Header("Authorization") title: String): Response<MovieModel>
}