package com.smartherd.pokemon.list

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smartherd.pokemon.R
import com.smartherd.pokemon.databinding.ListItemBinding
import com.smartherd.pokemon.models.PokemonData
import com.smartherd.pokemon.models.PokemonTypeColor

class PokemonListViewHolder(
    private val binding: ListItemBinding,
    private val onPokemonItemClicked: (pokemon: PokemonData) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private val pokemonImage: ImageView = itemView.findViewById(R.id.pokemon_image)
    private val context: Context = itemView.context
    private lateinit var pokemon: PokemonData

    init {
        binding.root.setOnClickListener{
            onPokemonItemClicked.invoke(pokemon)
        }
    }


    fun bindPokemon(pokemon: PokemonData) {
        this.pokemon = pokemon
        binding.pokemon = pokemon

        val backgroundColor = PokemonTypeColor.fromTypeName(pokemon.typeName).getColor(context)
        binding.backgroundColor = backgroundColor

        Glide.with(context)
            .load(pokemon.imageUrl)
            .into(pokemonImage)

    }

}