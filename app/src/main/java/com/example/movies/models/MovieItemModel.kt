package com.example.movies.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Movies")
data class MovieItemModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String,
    var overview: String,
    var poster_path: String,
    var release_date: String,
    var isFav: Boolean = false
): Serializable