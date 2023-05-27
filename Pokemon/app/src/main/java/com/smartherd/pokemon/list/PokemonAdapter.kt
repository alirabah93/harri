package com.smartherd.pokemon.list

import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.smartherd.pokemon.R
import com.smartherd.pokemon.detail.PokemonDetailActivity
import com.smartherd.pokemon.models.PokemonData

class PokemonAdapter(private var pokemonList: List<PokemonData>) :
    RecyclerView.Adapter<PokemonViewHolder>() {

    private var positionChangeListener: OnPositionChangeListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bindPokemon(pokemonList[position])
        println("this is pokemonList: $pokemonList")
        holder.itemView.setOnClickListener { v ->
            val context = v.context
            val selectedPokemon = pokemonList[position]
            val intent = Intent(context, PokemonDetailActivity::class.java).apply {
                putExtra(PokemonDetailActivity.ARG_SELECTED_POKEMON, selectedPokemon)
            }
            context.startActivity(intent)
        }

        if (position == pokemonList.size - 1) {
            positionChangeListener?.onReachedBottomList()
        }
    }


    override fun getItemCount(): Int = pokemonList.size

    fun addItems(newItems: MutableList<PokemonData>) {
        pokemonList += newItems
        notifyDataSetChanged()
    }

    fun clearItems() {
        pokemonList = emptyList()
        notifyDataSetChanged()
    }

    interface OnPositionChangeListener {
        fun onReachedBottomList()
    }

    fun setOnPositionChangeListener(listener: OnPositionChangeListener) {
        positionChangeListener = listener
    }
}