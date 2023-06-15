package com.smartherd.pokemon.list

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.smartherd.pokemon.models.Pokemon
import com.smartherd.pokemon.models.PokemonData
import com.smartherd.pokemon.models.PokemonDetail
import com.smartherd.pokemon.models.PokemonListResponse
import com.smartherd.pokemon.services.PokemonService
import com.smartherd.pokemon.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object PokemonRepository {

//    do snack bar
    private val pokemonService: PokemonService = ServiceBuilder.buildService(PokemonService::class.java)
    private val allPokemons = mutableListOf<PokemonData>()
    private lateinit var pokemonList: List<Pokemon>

    private var offset = 0
    private const val PAGE_LIMIT = 20


    fun loadPokemonPage(offset: Int, callback: PokemonCallback){
        pokemonService.getPokemonList(PAGE_LIMIT, offset).enqueue(object : Callback<PokemonListResponse> {
            override fun onResponse(
                call: Call<PokemonListResponse>,
                response: Response<PokemonListResponse>
            ) {
                if (response.isSuccessful) {
                    handleResponse(response, callback)
                    Log.e("error555", "this is error")
                } else {
                    Log.e("error444", "this is error")
                    callback.onError("Failed to fetch pokemons.")
                }
            }
            override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                handleError(t)
            }
        })
    }

    fun searchPokemon(searchName: String, callback: PokemonCallback){
        pokemonService.getPokemonList().enqueue(object : Callback<PokemonListResponse> {
            override fun onResponse(
                call: Call<PokemonListResponse>,
                response: Response<PokemonListResponse>
            ) {
                if (response.isSuccessful) {
                    handleSearch(response, callback, searchName)
//                    Log.i("pokemonList", allPokemons.toString())
                } else {
                    Log.e("error444", "this is error")
                    callback.onError("Failed to fetch pokemons.")
                }
            }

            override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                handleError(t)
            }
        })
    }


    private fun handleResponse(
        response: Response<PokemonListResponse>,
        callback: PokemonCallback
    ) {
        val pokemonResponse = response.body()
        pokemonList = pokemonResponse?.results?: emptyList()
        val processedPokemonList = mutableListOf<PokemonData>()
        for (pokemon in pokemonList) {
            val id = extractPokemonId(pokemon.url)
            getPokemonData(id, pokemon) { pokemonData ->
                processedPokemonList.add(pokemonData)
                if (processedPokemonList.size == pokemonList.size) {
                    allPokemons.addAll(processedPokemonList)
                    callback.onSuccess(allPokemons)
                }
            }
        }
    }
    private fun handleSearch(
        response: Response<PokemonListResponse>,
        callback: PokemonCallback,
        searchName: String
    ) {
        val pokemonResponse = response.body()
        pokemonList = pokemonResponse?.results?.filter { pokemon ->
            pokemon.name.startsWith(searchName, ignoreCase = true)
        } ?: emptyList()
        val processedPokemonList = mutableListOf<PokemonData>()
        for (pokemon in pokemonList) {
            val id = extractPokemonId(pokemon.url)
            getPokemonData(id, pokemon) { pokemonData ->
                processedPokemonList.add(pokemonData)
                callback.onSuccess(processedPokemonList)
            }
        }
    }

    private fun getPokemonData(id: Int, pokemon: Pokemon, callback: (PokemonData) -> Unit) {
        pokemonService.getPokemonDetail(id).enqueue(object : Callback<PokemonDetail> {
            override fun onResponse(
                call: Call<PokemonDetail>,
                response: Response<PokemonDetail>
            ) {
                if (response.isSuccessful) {
                    val pokemonDetail = response.body()
                    val typeName = pokemonDetail?.types?.getOrNull(0)?.type?.name?: ""
                    val weight = pokemonDetail?.weight?: 0
                    val height = pokemonDetail?.height?: 0
                    val stats = pokemonDetail?.stats?: emptyList()

                    val imageUrl =
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
                    val pokemonData =
                        PokemonData(pokemon.name, id, typeName, imageUrl, weight, height, stats)
                    callback(pokemonData)
                } else if (response.code() == 401) {
                    showErrorMessage("Error code 401. Please try again.")
                } else {
                    showErrorMessage("Failed to retrieve Pokemon data")
                }
            }

            override fun onFailure(call: Call<PokemonDetail>, t: Throwable) {
                handleError(t)
            }
        })
    }

    private fun handleError(t: Throwable) {
        Log.e("Failed Api", "Failed Api with error code: ${t.message}")
    }

    private fun showErrorMessage(message: String) {
        Log.e("Failed Api", "error code: $message")
    }

    private fun extractPokemonId(pokemonUrl: String): Int {
        val regex = Regex("""/pokemon/(\d+)/""")
        val matchResult = regex.find(pokemonUrl)
        return matchResult?.groupValues?.get(1)?.toIntOrNull() ?: -1
    }

    interface PokemonCallback {
        fun onSuccess(pokemons: List<PokemonData>)
        fun onError(error: String)
    }


}
