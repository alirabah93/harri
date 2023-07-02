package com.smartherd.pokemon.list

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smartherd.pokemon.R
import com.smartherd.pokemon.data.PokemonRepository
import com.smartherd.pokemon.detail.PokemonDetailActivity
import com.smartherd.pokemon.models.PokemonData

private const val VIEW_TYPE_POKEMON = 0
private const val VIEW_TYPE_PROGRESSBAR = 1

class PokemonAdapter(private var pokemonList: List<PokemonData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var positionChangeListener: OnPositionChangeListener? = null
    private var hasMorePages = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_POKEMON) {
            PokemonViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
            )
        } else {
            LoadingViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.view_progress_bar, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)

        if (viewType == VIEW_TYPE_POKEMON) {
            (holder as PokemonViewHolder).bindPokemon(pokemonList[position])
            holder.itemView.setOnClickListener { v ->
                val context = v.context
                val selectedPokemon = pokemonList[position]
                val intent = Intent(context, PokemonDetailActivity::class.java).apply {
                    putExtra(PokemonDetailActivity.ARG_SELECTED_POKEMON_ID, selectedPokemon.id)
                }
                context.startActivity(intent)
            }
        } else {
            positionChangeListener?.onReachedBottomList()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            VIEW_TYPE_PROGRESSBAR
        } else {
            VIEW_TYPE_POKEMON
        }
    }

    override fun getItemCount(): Int {
        return if (pokemonList.isEmpty()) {
            0
        } else {
            pokemonList.size + if (hasMorePages) 1 else 0
        }
    }

    fun addItems(newItems: List<PokemonData>) {
        pokemonList += newItems
        hasMorePages = newItems.size == PokemonRepository.PAGE_LIMIT
    }

    @SuppressLint("NotifyDataSetChanged")
    fun searchItems(newItems: List<PokemonData>) {
        pokemonList = newItems
        hasMorePages = newItems.size == PokemonRepository.PAGE_LIMIT
        notifyDataSetChanged()
    }

    fun clearItems() {
        pokemonList = emptyList()
        hasMorePages = true
    }

    fun setOnPositionChangeListener(listener: OnPositionChangeListener) {
        positionChangeListener = listener
    }

}