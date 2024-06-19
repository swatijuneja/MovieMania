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
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(overview)
        parcel.writeString(poster_path)
        parcel.writeString(release_date)
        parcel.writeByte(if (isFav) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieItemModel> {
        override fun createFromParcel(parcel: Parcel): MovieItemModel {
            return MovieItemModel(parcel)
        }

        override fun newArray(size: Int): Array<MovieItemModel?> {
            return arrayOfNulls(size)
        }
    }
}