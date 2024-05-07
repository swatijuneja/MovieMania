package com.example.movies.models

import android.os.Parcel
import android.os.Parcelable

data class FavoriteMovie(
    var isFavorite: Boolean = false,
    var movieItem: MovieItemModel
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(MovieItemModel::class.java.classLoader)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (isFavorite) 1 else 0)
        parcel.writeParcelable(movieItem, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FavoriteMovie> {
        override fun createFromParcel(parcel: Parcel): FavoriteMovie {
            return FavoriteMovie(parcel)
        }

        override fun newArray(size: Int): Array<FavoriteMovie?> {
            return arrayOfNulls(size)
        }
    }

}