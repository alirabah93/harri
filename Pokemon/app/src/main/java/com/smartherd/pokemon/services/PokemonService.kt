package com.smartherd.pokemon.services


import com.smartherd.pokemon.models.Pokemon
import com.smartherd.pokemon.models.PokemonListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface PokemonService {

    @GET("pokemon")
    fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<PokemonListResponse>
}
