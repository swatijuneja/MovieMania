package com.example.movies.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private var instance: Retrofit? = null
    const val BASE_URL = "https://api.themoviedb.org/"
    const val token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxZjU2Y2YwNTY5YTRjYWY3OGEyY2E2OTcwOTUwMDQ0NSIsInN1YiI6IjY2MjNjYjYzNjJmMzM1MDE2NGQ3YjAxYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.6zKI14GNVqPj-aEJR8ULli_TqGi9PLzrRzNvSGFaVpo"

    private fun getBuilder(): Retrofit = synchronized(this) {
        if(instance == null)
        {
            synchronized(this) {
                instance = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                return instance!!
            }
        }
        return instance!!
    }

    fun getMovieInterface(): MovieInterface = getBuilder().create(MovieInterface::class.java)

}