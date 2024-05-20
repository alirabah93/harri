package com.smartherd.pokemon.detail


import androidx.recyclerview.widget.RecyclerView
import com.smartherd.pokemon.databinding.DetailItemBinding
import com.smartherd.pokemon.models.PokemonStat

class PokemonDetailViewHolder(private val binding: DetailItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindPokemonDetail(stat: PokemonStat) {
        binding.stat = stat
    }
}
