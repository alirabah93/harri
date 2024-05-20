package com.smartherd.pokemon.models

import com.google.gson.annotations.SerializedName

data class PokemonDetail(
    @SerializedName("height") val height: Int,
    @SerializedName("weight") val weight: Int,
    @SerializedName("types") val types: List<PokemonType>,
    @SerializedName("stats") val stats: List<PokemonStat>
)

data class PokemonType(
    @SerializedName("slot") val slot: Int,
    @SerializedName("type") val type: TypeObject
)

data class TypeObject(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)

data class PokemonStat(
    @SerializedName("base_stat") val base_stat: Int,
    @SerializedName("stat") val stat: StatObject
)

data class StatObject(
    @SerializedName("name") val name: String
)

