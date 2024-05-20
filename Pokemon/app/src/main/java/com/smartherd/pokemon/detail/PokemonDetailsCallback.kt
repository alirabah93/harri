package com.smartherd.pokemon.detail

import com.smartherd.pokemon.models.PokemonData

interface PokemonDetailsCallback {
    fun onSuccess(pokemon: PokemonData)
    fun onError(error: String)
}