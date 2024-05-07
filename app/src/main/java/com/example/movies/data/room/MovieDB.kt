package com.example.movies.data.room

import androidx.room.RoomDatabase

abstract class MovieDB: RoomDatabase() {

    abstract fun getDao(): MovieDao
}