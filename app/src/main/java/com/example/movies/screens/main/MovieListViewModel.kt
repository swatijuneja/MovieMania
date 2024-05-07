package com.example.movies.screens.main

import android.icu.text.CaseMap.Title
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movies.R
import com.example.movies.data.retrofit.MovieInterface
import com.example.movies.data.room.MovieDao
import com.example.movies.models.FavoriteMovie
import com.example.movies.models.MovieItemModel
import com.example.movies.screens.favorite.FavMovieFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListViewModel(private val movieClient: MovieInterface, private val movieDao: MovieDao): ViewModel() {

    var movieList = MutableLiveData<List<MovieItemModel>>()

    fun getMovies() =
        CoroutineScope(Dispatchers.IO).launch {
            movieList.postValue(movieClient
                .getTopRatedMovieList("Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxZjU2Y2YwNTY5YTRjYWY3OGEyY2E2OTcwOTUwMDQ0NSIsInN1YiI6IjY2MjNjYjYzNjJmMzM1MDE2NGQ3YjAxYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.6zKI14GNVqPj-aEJR8ULli_TqGi9PLzrRzNvSGFaVpo")
                .body()!!.results)
        }

    fun deleteFavMovie(title: String) {
        CoroutineScope(Dispatchers.IO).launch {
            movieDao.removeFavMovie(title)
        }
    }

    fun showFavMovieList(item: MovieItemModel) {

    }
}

class MovieViewModelFactory(private val client: MovieInterface, private val movieDao: MovieDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            return MovieListViewModel(client, movieDao) as T
        }
        throw IllegalArgumentException("UNKNOWN VIEW MODEL CLASS")
    }
}