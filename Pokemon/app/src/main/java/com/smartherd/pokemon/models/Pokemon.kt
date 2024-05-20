package com.smartherd.pokemon.models

import com.google.gson.annotations.SerializedName

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Pokemon>
)

data class Pokemon(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)

data class PokemonData(
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: Int,
    @SerializedName("typeName") val typeName: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("weight") val weight: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("stats") val stats: List<PokemonStat>
)


