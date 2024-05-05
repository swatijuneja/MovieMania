package com.example.movies.screens.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movies.data.retrofit.MovieInterface
import com.example.movies.models.MovieItemModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListViewModel(private val movieClient: MovieInterface): ViewModel() {

    var movieList = MutableLiveData<List<MovieItemModel>>()

    fun getMovies() =
        CoroutineScope(Dispatchers.IO).launch {
            movieList.postValue(movieClient
                .getTopRatedMovieList("Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxZjU2Y2YwNTY5YTRjYWY3OGEyY2E2OTcwOTUwMDQ0NSIsInN1YiI6IjY2MjNjYjYzNjJmMzM1MDE2NGQ3YjAxYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.6zKI14GNVqPj-aEJR8ULli_TqGi9PLzrRzNvSGFaVpo")
                .body()!!.results)
        }
}

class MovieViewModelFactory(private val client: MovieInterface): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            return MovieListViewModel(client) as T
        }
        throw IllegalArgumentException("UNKNOWN VIEW MODEL CLASS")
    }
}