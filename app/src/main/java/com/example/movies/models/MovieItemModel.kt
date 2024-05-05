package com.example.movies.models

import android.os.Parcel
import android.os.Parcelable

data class MovieItemModel(
    var title: String,
    var overview: String,
    var poster_path: String,
    var release_date: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(overview)
        parcel.writeString(release_date)
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