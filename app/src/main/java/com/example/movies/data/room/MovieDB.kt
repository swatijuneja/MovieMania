package com.example.movies.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.models.MovieItemModel

@Database(entities = [MovieItemModel::class], version = 1)
abstract class MovieDB: RoomDatabase() {

    abstract fun getDao(): MovieDao
}