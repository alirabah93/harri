package com.smartherd.pokemon.services


import com.smartherd.pokemon.models.PokemonDetail
import com.smartherd.pokemon.models.PokemonListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url


interface PokemonService {

    @GET("pokemon")
    fun getPokemonList(
        @Query("offset") offset: Int? = null,
        @Query("limit") limit: Int? = null
    ): Call<PokemonListResponse>

    @GET("pokemon/{id}")
    fun getPokemonDetail(@Path("id") id: Int): Call<PokemonDetail>

    @GET
    fun getPokemonListByUrl(@Url url: String): Call<PokemonListResponse>

}
