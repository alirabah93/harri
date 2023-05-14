package com.smartherd.pokemon.models


data class PokemonTypeSlot(
    val types: List<PokemonType>
)

data class PokemonType(
    val slot: Int,
    val type: TypeObject
)

data class TypeObject(
    val name: String,
    val url: String
)
