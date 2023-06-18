package com.smartherd.pokemon.detail


import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.smartherd.pokemon.R
import com.smartherd.pokemon.models.PokemonStat

class PokemonDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val pokemonStatInt: TextView = itemView.findViewById(R.id.stat_int)
    private val pokemonStatName: TextView = itemView.findViewById(R.id.stat_name)
    private val statProgress: ProgressBar = itemView.findViewById(R.id.stat_progress_bar)

    fun bindPokemonDetail(stat: PokemonStat) {
        pokemonStatInt.text = stat.base_stat.toString()
        pokemonStatName.text = stat.stat.name
        statProgress.progress = stat.base_stat * 100 / 255
    }
}
