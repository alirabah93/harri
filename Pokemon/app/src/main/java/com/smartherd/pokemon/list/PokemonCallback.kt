package com.smartherd.pokemon.list

import com.smartherd.pokemon.models.PokemonData

interface PokemonCallback {
    fun onSuccess(pokemons: List<PokemonData>)
    fun onError(error: String)
}