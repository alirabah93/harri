package com.smartherd.pokemon.list

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.smartherd.pokemon.R
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
                    if (response.isSuccessful) {
                        response.body()?.types?.getOrNull(0)?.type?.name?.let { pokemonTypeName ->
                            val color = getPokemonTypeColor(pokemonTypeName)
                            imageView.setBackgroundColor(color)
                        }
                    }
                }

                override fun onFailure(call: Call<PokemonTypeSlot>, t: Throwable) {
                    // Handle failure
                }
            })
        }
    }

    private fun getPokemonTypeColor(pokemonTypeName: String): Int {
        val colorResId = when (pokemonTypeName) {
            "fire" -> R.color.fire
            "water" -> R.color.water
            "grass" -> R.color.grass
            "electric" -> R.color.electric
            "ice" -> R.color.ice
            "fighting" -> R.color.fighting
            "poison" -> R.color.poison
            "ground" -> R.color.ground
            "flying" -> R.color.flying
            "psychic" -> R.color.psychic
            "bug" -> R.color.bug
            "rock" -> R.color.rock
            "ghost" -> R.color.ghost
            "dragon" -> R.color.dragon
            "dark" -> R.color.dark
            "steel" -> R.color.steel
            "fairy" -> R.color.fairy
            else -> R.color.normal
        }
        return ContextCompat.getColor(context, colorResId)
    }
}
