package com.smartherd.pokemon.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smartherd.pokemon.R
import com.smartherd.pokemon.models.PokemonStat


class PokemonDetailAdapter(private var pokemonStatDetail: List<PokemonStat>) :
    RecyclerView.Adapter<PokemonDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.detail_item, parent, false)
        return PokemonDetailViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokemonStatDetail.size
    }

    override fun onBindViewHolder(holder: PokemonDetailViewHolder, position: Int) {
        val stat = pokemonStatDetail[position]
        holder.bindPokemonDetail(stat)
    }

    fun setStats(stats: List<PokemonStat>) {
        this.pokemonStatDetail = stats
        notifyDataSetChanged()
    }
}

