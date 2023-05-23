package com.smartherd.pokemon.list

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.smartherd.pokemon.models.Pokemon
import com.smartherd.pokemon.models.PokemonData
import com.smartherd.pokemon.models.PokemonListResponse
import com.smartherd.pokemon.models.PokemonTypeSlot
import com.smartherd.pokemon.services.PokemonService
import com.smartherd.pokemon.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

private const val PAGE_SIZE = 20

class PokemonLoader(
    private val activity: AppCompatActivity,
    private val recyclerView: RecyclerView,
    private val progressBar: ProgressBar
) {

    private val pokemonService: PokemonService =
        ServiceBuilder.buildService(PokemonService::class.java)
    private var offset = 0
    private var searchName = ""

    fun loadPokemon() {
        progressBar.visibility = View.VISIBLE

        val requestCall = pokemonService.getPokemonList(PAGE_SIZE, offset)
        requestCall.enqueue(object : Callback<PokemonListResponse> {
            override fun onResponse(
                call: Call<PokemonListResponse>,
                response: Response<PokemonListResponse>
            ) {
                handleResponse(response)
            }

            override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                handleError(t)
            }
        })
    }

    private fun handleResponse(response: Response<PokemonListResponse>) {
        if (response.isSuccessful) {
            val pokemonResponse = response.body()
            val pokemonList: List<Pokemon> = pokemonResponse?.results?.filter { pokemon ->
                pokemon.name.startsWith(searchName, ignoreCase = true)
            } ?: emptyList()

            val processedPokemonList = mutableListOf<PokemonData>()

            for (pokemon in pokemonList) {
                val id = extractPokemonId(pokemon.url)
                getPokemonTypeName(id) { typeName ->
                    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

                    val pokemonData = PokemonData(pokemon.name, id, typeName, imageUrl)
                    processedPokemonList.add(pokemonData)

                    if (processedPokemonList.size == pokemonList.size) {
                        val adapter = recyclerView.adapter as? PokemonAdapter ?: PokemonAdapter(processedPokemonList)
                        adapter.addItems(processedPokemonList)

                        progressBar.visibility = View.GONE
                    }
                }
            }
        } else if (response.code() == 401) {
            showErrorMessage("Your session has expired. Please Login again.")
        } else {
            showErrorMessage("Failed to retrieve items")
        }
    }

    private fun handleError(t: Throwable) {
        Log.e("Failed Api", "Failed Api with error code: ${t.message}")
        showErrorMessage("Error Occurred: ${t.toString()}")
        progressBar.visibility = View.GONE
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    fun setSearchName(name: String) {
        searchName = name
        offset = 0
    }

    fun increaseOffset() {
        offset += PAGE_SIZE
    }

    private fun extractPokemonId(pokemonUrl: String): Int {
        val regex = Regex("""/pokemon/(\d+)/""")
        val matchResult = regex.find(pokemonUrl)
        return matchResult?.groupValues?.get(1)?.toIntOrNull() ?: -1
    }

    private fun getPokemonTypeName(id: Int, callback: (String) -> Unit) {
        val requestCall: Call<PokemonTypeSlot> = pokemonService.getPokemonType(id)
        requestCall.enqueue(object : Callback<PokemonTypeSlot> {
            override fun onResponse(
                call: Call<PokemonTypeSlot>,
                response: Response<PokemonTypeSlot>
            ) {
                if (response.isSuccessful) {
                    val pokemonType = response.body()?.types?.getOrNull(0)?.type?.name ?: ""
                    callback(pokemonType)
                } else if (response.code() == 401) {
                    showErrorMessage("Your session has expired. Please Login again.")
                } else {
                    showErrorMessage("Failed to retrieve items")
                }
            }

            override fun onFailure(call: Call<PokemonTypeSlot>, t: Throwable) {
                handleError(t)
            }
        })
    }

}
