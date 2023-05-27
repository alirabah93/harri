package com.smartherd.pokemon.detail

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smartherd.pokemon.R
import com.smartherd.pokemon.models.PokemonStat

class PokemonDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val pokemonStatInt: TextView = itemView.findViewById(R.id.stat_int)
    private val pokemonStatName: TextView = itemView.findViewById(R.id.stat_name)

    fun bindPokemonDetail(stat: PokemonStat) {
//        println("this is PokemonStat: $stat")
        pokemonStatInt.text = stat.base_stat.toString()
        println("this is PokemonStat: ${stat.base_stat}")
        pokemonStatName.text = stat.stat.name
    }
}
