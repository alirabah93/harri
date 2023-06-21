package com.smartherd.pokemon.list

import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.smartherd.pokemon.detail.PokemonDetailsCallback
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

    private val pokemonService: PokemonService =
        ServiceBuilder.buildService(PokemonService::class.java)
    private val allPokemons = mutableListOf<PokemonData>()
    private lateinit var pokemonList: List<Pokemon>
    private lateinit var snack: Snackbar
    private var pokemonResponse: PokemonListResponse? = null
    private var processedPokemonList = mutableListOf<PokemonData>()
    private const val PAGE_LIMIT = 20


    fun loadPokemonPage(offset: Int, callback: PokemonCallback, view: View) {
        if (allPokemons.size > offset) {
            callback.onSuccess(allPokemons)
            return
        }
        pokemonService.getPokemonList(offset, PAGE_LIMIT)
            .enqueue(object : Callback<PokemonListResponse> {
                override fun onResponse(
                    call: Call<PokemonListResponse>,
                    response: Response<PokemonListResponse>
                ) {
                    if (response.isSuccessful) {
                        pokemonResponse = response.body()
                        pokemonList = pokemonResponse?.results ?: emptyList()
                        processedPokemonList.clear()
                        for (pokemon in pokemonList) {
                            val id = extractPokemonId(pokemon.url)
                            getPokemonData(id, pokemon) { pokemonData ->
                                processedPokemonList.add(pokemonData)
                                if (processedPokemonList.size == pokemonList.size) {
                                    allPokemons.addAll(processedPokemonList)
                                    callback.onSuccess(processedPokemonList)
                                }
                            }
                        }
                        snack = Snackbar.make(
                            view,
                            "+20 Pokemons has been loaded",
                            Snackbar.LENGTH_LONG
                        )
                        snack.show()
                        Log.i("Load Success", "Pokemons loaded successfully")
                    } else {
                        snack = Snackbar.make(
                            view,
                            "failed to fetch Pokemons, pull to refresh",
                            Snackbar.LENGTH_LONG
                        )
                        snack.show()
                        Log.e("Load Error", "Failed to fetch pokemons.")
                        callback.onError("Failed to fetch pokemons.")
                    }
                }

                override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                    Log.e("On Failure Error", "Failure Error description: ${t.message}")
                }
            })
    }

    fun searchPokemonName(offset: Int, searchName: String, callback: PokemonCallback, view: View) {
        pokemonService.getPokemonList(offset, 1000)
            .enqueue(object : Callback<PokemonListResponse> {
                override fun onResponse(
                    call: Call<PokemonListResponse>,
                    response: Response<PokemonListResponse>
                ) {
                    if (response.isSuccessful) {
                        pokemonResponse = response.body()
                        pokemonList = pokemonResponse?.results?.filter { pokemon ->
                            pokemon.name.startsWith(searchName, ignoreCase = true)
                        } ?: emptyList()
                        processedPokemonList.clear()
                        for (pokemon in pokemonList) {
                            val id = extractPokemonId(pokemon.url)
                            getPokemonData(id, pokemon) { pokemonData ->
                                processedPokemonList.add(pokemonData)
                                if (processedPokemonList.size == pokemonList.size) {
                                    println("this is processedPokemonList: $processedPokemonList")
                                    callback.onSuccess(processedPokemonList)
                                }
                            }
                        }
                        snack = Snackbar.make(view, "Search is completed", Snackbar.LENGTH_LONG)
                        snack.show()
                        Log.i("Search Success", "Search completed successfully")
                    } else {
                        callback.onError("Failed to search pokemons.")
                    }
                }

                override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                    Log.e("On Failure Error", "Failure Error description: ${t.message}")
                }
            })
    }


    fun loadPokemonDetails(id: Int, callback: PokemonDetailsCallback) {

        val selectedPokemon = allPokemons.find { it.id == id }
        if (selectedPokemon != null) {
            callback.onSuccess(selectedPokemon)
        } else {
            callback.onError("Failed to fetch pokemon's details.")
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
                    val typeName = pokemonDetail?.types?.getOrNull(0)?.type?.name ?: ""
                    val weight = pokemonDetail?.weight ?: 0
                    val height = pokemonDetail?.height ?: 0
                    val stats = pokemonDetail?.stats ?: emptyList()

                    val imageUrl =
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
                    val pokemonData =
                        PokemonData(pokemon.name, id, typeName, imageUrl, weight, height, stats)
                    callback(pokemonData)
                    Log.i("Pokemons Data success", "Fetching Pokemons data completed successfully")
                } else if (response.code() == 401) {
                    Log.e("Pokemons Data Error 401", "Error code 401. Please try again.")
                } else {

                    Log.e("Pokemons Data Error", "Unknown Error. Please try again.")
                }
            }

            override fun onFailure(call: Call<PokemonDetail>, t: Throwable) {
                Log.e("On Failure Error", "Failure Error description: ${t.message}")
            }
        })
    }

    private fun extractPokemonId(pokemonUrl: String): Int {
        val regex = Regex("""/pokemon/(\d+)/""")
        val matchResult = regex.find(pokemonUrl)
        return matchResult?.groupValues?.get(1)?.toIntOrNull() ?: -1
    }

    fun clearAllPokemons() {
        allPokemons.clear()
    }
}
