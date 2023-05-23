package com.smartherd.pokemon.list

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smartherd.pokemon.R
import com.smartherd.pokemon.models.Pokemon

class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val pokemonImage: ImageView = itemView.findViewById(R.id.pokemon_image)
    private val pokemonName: TextView = itemView.findViewById(R.id.pokemon_name)
    private val context: Context = itemView.context
    var pokemon: Pokemon? = null

    fun bindPokemon(pokemon: Pokemon) {
        pokemonName.text = pokemon.name
        val pokemonId = extractPokemonId(pokemon.url)
        val pokemonImageUrl =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"

        Glide.with(context)
            .load(pokemonImageUrl)
            .into(pokemonImage)

        PokemonTypeColorMapper(context).setPokemonTypeColor(pokemonId, pokemonImage)
    }

    private fun extractPokemonId(pokemonUrl: String): String? {
        val regex = Regex("""/pokemon/(\d+)/""")
        return regex.find(pokemonUrl)?.groupValues?.get(1)
    }
}
