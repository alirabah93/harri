package com.smartherd.pokemon.models

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
)

data class StatObject(
    val name: String
)

