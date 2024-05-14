package com.example.movies.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.movies.models.MovieItemModel

@Dao
interface MovieDao {

    @Insert
    fun saveFavMovie(item: MovieItemModel)

    @Query("DELETE FROM Movies WHERE title = :movieTitle")
    fun removeFavMovie(movieTitle: String)

    @Query("SELECT * FROM Movies WHERE isFav = 1")
    fun getAllFavMovies(): MutableList<MovieItemModel>
}