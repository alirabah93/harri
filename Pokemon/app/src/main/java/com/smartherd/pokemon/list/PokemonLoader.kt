package com.smartherd.pokemon.list

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.smartherd.pokemon.models.Pokemon
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

            val adapter = recyclerView.adapter as? PokemonAdapter ?: PokemonAdapter(pokemonList)
            adapter.addItems(pokemonList)
            progressBar.visibility = View.GONE
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
}
