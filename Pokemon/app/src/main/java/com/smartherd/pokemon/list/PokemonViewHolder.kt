package com.smartherd.pokemon.list

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smartherd.pokemon.R
import com.smartherd.pokemon.models.Pokemon
import com.smartherd.pokemon.models.PokemonData
import com.smartherd.pokemon.models.PokemonTypeColor

class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val pokemonImage: ImageView = itemView.findViewById(R.id.pokemon_image)
    private val pokemonName: TextView = itemView.findViewById(R.id.pokemon_name)
    private val context: Context = itemView.context
    var pokemon: Pokemon? = null

    fun bindPokemon(pokemon: PokemonData) {
        pokemonName.text = pokemon.name

        val pokemonType = PokemonTypeColor.fromTypeName(pokemon.typeName)
        val color = pokemonType.getColor(context)
        pokemonImage.setBackgroundColor(color)

        Glide.with(context)
            .load(pokemon.imageUrl)
            .into(pokemonImage)

    }

}