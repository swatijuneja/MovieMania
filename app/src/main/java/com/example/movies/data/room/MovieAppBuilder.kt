package com.example.movies.data.room

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase

class MovieAppBuilder: Application() {

    var builder = Room.databaseBuilder(this, MovieDB::class.java, "movieDatabase")
        .build()
}