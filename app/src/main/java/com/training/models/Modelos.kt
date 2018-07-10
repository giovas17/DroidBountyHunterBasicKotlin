package com.training.models

import android.os.Parcel
import android.os.Parcelable

/**
 * @author Giovani Gonzalez
 * Created darkgeat on 7/9/18
 */

data class Fugitivo (val id: Int = 0, var name: String = "", var status: Int = 0) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(status)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Fugitivo> {
        override fun createFromParcel(parcel: Parcel): Fugitivo {
            return Fugitivo(parcel)
        }

        override fun newArray(size: Int): Array<Fugitivo?> {
            return arrayOfNulls(size)
        }
    }
}