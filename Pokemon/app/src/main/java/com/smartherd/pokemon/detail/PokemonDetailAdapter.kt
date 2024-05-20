package com.smartherd.pokemon.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.smartherd.pokemon.R
import com.smartherd.pokemon.databinding.DetailItemBinding
import com.smartherd.pokemon.list.PokemonListViewHolder
import com.smartherd.pokemon.models.PokemonStat


class PokemonDetailAdapter(private var pokemonStatDetail: List<PokemonStat>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: DetailItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.detail_item,
            parent,
            false
        )
        return PokemonDetailViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pokemonStatDetail.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PokemonDetailViewHolder).bindPokemonDetail(pokemonStatDetail[position])
    }

}

