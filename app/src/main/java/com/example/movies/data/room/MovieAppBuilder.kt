package com.example.movies.data.room

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

class MovieAppBuilder(private val context: Context): Application() {

    var builder = Room.databaseBuilder(context, MovieDB::class.java, "movieDatabase")
        .build()
}