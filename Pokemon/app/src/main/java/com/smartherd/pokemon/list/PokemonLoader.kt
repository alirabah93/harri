package com.smartherd.pokemon.list

import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.smartherd.pokemon.R
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
    private val progressBar: ProgressBar,
    private val emptyErrorContainer: FrameLayout
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
                getPokemonTypeName(id) { typeName ->
                    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

                    val pokemonData = PokemonData(pokemon.name, id, typeName, imageUrl)
                    processedPokemonList.add(pokemonData)

                    if (processedPokemonList.size == pokemonList.size) {
//                        processedPokemonList.clear()
                        if (processedPokemonList.isEmpty()) {
                            showEmptyLayout()
                        } else {
                            hideEmptyLayout()
                            val adapter =
                                recyclerView.adapter as? PokemonAdapter ?: PokemonAdapter(processedPokemonList)
                            adapter.addItems(processedPokemonList)
                        }

                    }
                    hideProgressBar()
                }
            }
        } else if (response.code() == 401) {
            showErrorLayout("Your session has expired. Please Login again.")
        } else {
            showErrorLayout("Failed to retrieve items")
        }
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

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    private fun showEmptyLayout() {
        emptyErrorContainer.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    private fun hideEmptyLayout() {
        emptyErrorContainer.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }


    private fun showErrorLayout(errorMessage: String) {
        emptyErrorContainer.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE

        // Show the error message in the Error layout
        val errorTextView: TextView = emptyErrorContainer.findViewById(R.id.errorTextView)
        errorTextView.text = errorMessage
    }

}
