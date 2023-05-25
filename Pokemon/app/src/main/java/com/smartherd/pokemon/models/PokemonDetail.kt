package com.smartherd.pokemon.models

import android.os.Parcel
import android.os.Parcelable


data class PokemonDetail(
    val height: Int,
    val weight: Int,
    val types: List<PokemonType>,
    val stats: List<PokemonStat>
)

data class PokemonType(
    val slot: Int,
    val type: TypeObject
)

data class TypeObject(
    val name: String,
    val url: String
)

data class PokemonStat(
    val base_stat: Int,
    val stat: StatObject
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readParcelable(StatObject::class.java.classLoader)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(base_stat)
        parcel.writeParcelable(stat, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PokemonStat> {
        override fun createFromParcel(parcel: Parcel): PokemonStat {
            return PokemonStat(parcel)
        }

        override fun newArray(size: Int): Array<PokemonStat?> {
            return arrayOfNulls(size)
        }
    }
}

data class StatObject(
    val name: String
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()!!)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StatObject> {
        override fun createFromParcel(parcel: Parcel): StatObject {
            return StatObject(parcel)
        }

        override fun newArray(size: Int): Array<StatObject?> {
            return arrayOfNulls(size)
        }
    }
}
