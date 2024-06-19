package com.example.movies.screens

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.movies.data.retrofit.RetrofitBuilder
import com.example.movies.data.retrofit.RetrofitBuilder.token
import com.example.movies.data.room.MovieDao
import com.example.movies.models.MovieItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieListViewModel(private val movieDao: MovieDao): ViewModel() {

    var movieList = MutableLiveData<MutableList<MovieItemModel>>()
    var favMovieList = MutableLiveData<MutableList<MovieItemModel>>()
    private var movieInterface = RetrofitBuilder.getMovieInterface()

    init {
        getMovies()
        getFavMovie()
    }

    private fun getMovies() =
        viewModelScope.launch {
            val movieModelList =  withContext(Dispatchers.IO) {
                movieInterface.getTopRatedMovieList("Bearer $token").body()?.results
            }
            movieModelList?.let { movieList.postValue(it) }
        }

    fun deleteFavMovie(title: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                movieDao.removeFavMovie(title)
            }
        }
    }

    fun saveMovie(item: MovieItemModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                movieDao.saveFavMovie(item)
            }
        }
    }

    fun getFavMovie()  {
        viewModelScope.launch {
                val k = withContext(Dispatchers.IO) {
                    movieDao.getAllFavMovies()
                }

            favMovieList.postValue(k)
            //Log.d("Swati", "k size ${k.size}")
        }
    }
}

class MovieViewModelFactory(private val movieDao: MovieDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            return MovieListViewModel(movieDao) as T
        }
        throw IllegalArgumentException("UNKNOWN VIEW MODEL CLASS")
    }
}