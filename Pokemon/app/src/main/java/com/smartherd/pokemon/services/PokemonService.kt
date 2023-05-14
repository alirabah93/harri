package com.smartherd.pokemon.services


import com.smartherd.pokemon.models.PokemonListResponse
import com.smartherd.pokemon.models.PokemonTypeSlot
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PokemonService {

    @GET("pokemon")
    fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<PokemonListResponse>

    @GET("pokemon/{id}")
    fun getPokemonType(@Path("id") id: String?): Call<PokemonTypeSlot>
}
