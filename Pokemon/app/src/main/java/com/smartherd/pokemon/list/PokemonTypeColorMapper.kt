package com.smartherd.pokemon.list

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.smartherd.pokemon.models.PokemonTypeSlot
import com.smartherd.pokemon.services.PokemonService
import com.smartherd.pokemon.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonTypeColorMapper(private val context: Context) {

    private val pokemonService: PokemonService =
        ServiceBuilder.buildService(PokemonService::class.java)

    fun setPokemonTypeColor(pokemonId: String?, imageView: ImageView) {
        pokemonId?.let { id ->
            val requestCall: Call<PokemonTypeSlot> = pokemonService.getPokemonType(id)
            requestCall.enqueue(object : Callback<PokemonTypeSlot> {
                override fun onResponse(
                    call: Call<PokemonTypeSlot>,
                    response: Response<PokemonTypeSlot>
                ) {
                    handleResponse(response, imageView)
                }

                override fun onFailure(call: Call<PokemonTypeSlot>, t: Throwable) {
                    handleError(t)
                }
            })
        }
    }

    private fun handleResponse(response: Response<PokemonTypeSlot>, imageView: ImageView) {
        if (response.isSuccessful) {
            val pokemonTypeName = response.body()?.types?.getOrNull(0)?.type?.name
            pokemonTypeName?.let {
                val color = PokemonTypeColor.getColor(it, context)
                imageView.setBackgroundColor(color)
            }
        } else if (response.code() == 401) {
            showErrorMessage("Your session has expired. Please Login again.")
        } else {
            showErrorMessage("Failed to retrieve items")
        }
    }

    private fun handleError(t: Throwable) {
        Log.e("Failed Api", "Failed API with error code: ${t.message}")
        showErrorMessage("Error Occurred: ${t.toString()}")
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(context.applicationContext, message, Toast.LENGTH_LONG).show()
    }
}
