package com.smartherd.pokemon.models

import android.os.Parcel
import android.os.Parcelable

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Pokemon>
)

data class Pokemon(
    val name: String,
    val url: String
)

data class PokemonData(
    val name: String,
    val id: Int,
    val typeName: String,
    val imageUrl: String,
    val weight: Int,
    val height: Int,
    val stats: List<PokemonStat>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        mutableListOf<PokemonStat>().apply {
            parcel.readList(this, PokemonStat::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(id)
        parcel.writeString(typeName)
        parcel.writeString(imageUrl)
        parcel.writeInt(weight)
        parcel.writeInt(height)
        parcel.writeList(stats)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PokemonData> {
        override fun createFromParcel(parcel: Parcel): PokemonData {
            return PokemonData(parcel)
        }

        override fun newArray(size: Int): Array<PokemonData?> {
            return arrayOfNulls(size)
        }
    }
}


