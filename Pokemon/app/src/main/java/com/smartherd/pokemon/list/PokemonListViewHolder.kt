package com.smartherd.pokemon.list


import androidx.recyclerview.widget.RecyclerView
import com.smartherd.pokemon.databinding.ListItemBinding
import com.smartherd.pokemon.models.PokemonData
import com.smartherd.pokemon.models.PokemonTypeColor

class PokemonListViewHolder(
    private val binding: ListItemBinding,
    private val onPokemonItemClicked: (pokemon: PokemonData) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var pokemon: PokemonData

    init {
        binding.root.setOnClickListener {
            onPokemonItemClicked.invoke(pokemon)
        }
    }


    fun bindPokemon(pokemon: PokemonData) {
        this.pokemon = pokemon
        binding.pokemon = pokemon

        val backgroundColor =
            PokemonTypeColor.fromTypeName(pokemon.typeName).getColor(itemView.context)
        binding.backgroundColor = backgroundColor
        binding.imageUrl = pokemon.imageUrl


    }

}