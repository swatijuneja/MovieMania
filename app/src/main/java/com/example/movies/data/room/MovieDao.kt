package com.example.movies.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert
    suspend fun saveFavMovie()

    @Query("DELETE FROM Movies WHERE title = :movieTitle")
    suspend fun removeFavMovie(movieTitle: String)
}