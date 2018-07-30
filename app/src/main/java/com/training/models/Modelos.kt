package com.training.models

import android.os.Parcel
import android.os.Parcelable

/**
 * @author Giovani Gonzalez
 * Created darkgeat on 7/9/18
 */

data class Fugitivo @JvmOverloads constructor(val id: Int, var name: String, var status: Int, var photo: String = "") : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(status)
        parcel.writeString(photo)
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