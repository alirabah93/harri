package com.smartherd.pokemon.list

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.smartherd.pokemon.models.Pokemon
import com.smartherd.pokemon.models.PokemonData
import com.smartherd.pokemon.models.PokemonDetail
import com.smartherd.pokemon.models.PokemonListResponse
import com.smartherd.pokemon.services.PokemonService
import com.smartherd.pokemon.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val PAGE_SIZE = 20

class PokemonLoader(
    private val activity: AppCompatActivity,
    private val recyclerView: RecyclerView,
    private val progressBar: ProgressBar,
    private val emptyTextView: TextView,
    private val errorTextView: TextView
) {

    private val pokemonService: PokemonService =
        ServiceBuilder.buildService(PokemonService::class.java)
    private var offset = 0
    private var searchName = ""


    fun loadPokemon() {
        showProgressBar()
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
                getPokemonData(id, pokemon) { pokemonData ->
                    processedPokemonList.add(pokemonData)
                    if (processedPokemonList.size == pokemonList.size) {
                        handleProcessedPokemonList(processedPokemonList)
                    }
                }
            }
        } else if (response.code() == 401) {
            showErrorLayout("Your session has expired. Please Login again.")
        } else {
            showErrorLayout("Failed to retrieve items")
        }
    }

    private fun handleProcessedPokemonList(processedPokemonList: List<PokemonData>) {
        if (processedPokemonList.isEmpty()) {
            showEmptyLayout()
        } else {
            hideEmptyLayout()
            val adapter =
                recyclerView.adapter as? PokemonAdapter ?: PokemonAdapter(processedPokemonList)
            adapter.addItems(processedPokemonList as MutableList<PokemonData>)
        }
        hideProgressBar()
    }

    private fun handleError(t: Throwable) {
        Log.e("Failed Api", "Failed Api with error code: ${t.message}")
        showErrorLayout("Error Occurred: ${t.toString()}")
        hideProgressBar()
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    fun setSearchName(name: String) {
        searchName = name
        offset = 0
        hideProgressBar()
    }

    fun increaseOffset() {
        offset += PAGE_SIZE
    }

    private fun extractPokemonId(pokemonUrl: String): Int {
        val regex = Regex("""/pokemon/(\d+)/""")
        val matchResult = regex.find(pokemonUrl)
        return matchResult?.groupValues?.get(1)?.toIntOrNull() ?: -1
    }

    private fun getPokemonData(id: Int, pokemon: Pokemon, callback: (PokemonData) -> Unit) {
        val requestCall: Call<PokemonDetail> = pokemonService.getPokemonDetail(id)
        requestCall.enqueue(object : Callback<PokemonDetail> {
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
                    println("this is pokemonData: $pokemonData")
                } else if (response.code() == 401) {
                    showErrorMessage("Your session has expired. Please login again.")
                } else {
                    showErrorMessage("Failed to retrieve Pokemon data")
                }
            }

            override fun onFailure(call: Call<PokemonDetail>, t: Throwable) {
                handleError(t)
            }
        })
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    private fun showEmptyLayout() {
        emptyTextView.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    private fun hideEmptyLayout() {
        emptyTextView.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    private fun showErrorLayout(errorMessage: String) {
        errorTextView.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        errorTextView.text = errorMessage
    }
}
