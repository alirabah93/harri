package com.smartherd.pokemon.list

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.smartherd.pokemon.R
import com.smartherd.pokemon.data.PokemonRepository
import com.smartherd.pokemon.databinding.ListItemBinding
import com.smartherd.pokemon.detail.PokemonDetailActivity
import com.smartherd.pokemon.models.PokemonData

private const val VIEW_TYPE_POKEMON = 0
private const val VIEW_TYPE_PROGRESSBAR = 1
private const val VIEW_TYPE_LOAD_MORE = 2

class PokemonListAdapter(private var pokemonList: List<PokemonData>,
                         private val onPokemonItemClicked: (pokemon: PokemonData) -> Unit
    ) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var positionChangeListener: OnPositionChangeListener? = null
    private var loadMoreClickListener: OnLoadMoreClickListener? = null
    private var hasMorePages = true
    private var searchMode = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_POKEMON -> {
                val binding: ListItemBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.list_item,
                    parent,
                    false
                )
                PokemonListViewHolder(binding, onPokemonItemClicked)
//                PokemonViewHolder(
//                    DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item, parent, false),
//                    onPokemonItemClicked
//                )
            }

            VIEW_TYPE_LOAD_MORE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.load_more_button, parent, false)
                val button = view.findViewById<Button>(R.id.load_more_button)
                button.setOnClickListener {
                    loadMoreClickListener?.onLoadMoreClick()
                }
                LoadingViewHolder(view)
            }

            else -> {
                LoadingViewHolder(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.view_progress_bar, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            VIEW_TYPE_POKEMON -> {
                (holder as PokemonListViewHolder).bindPokemon(pokemonList[position])
                holder.itemView.setOnClickListener { v ->
                    val context = v.context
                    val selectedPokemon = pokemonList[position]
                    val intent = Intent(context, PokemonDetailActivity::class.java).apply {
                        putExtra(PokemonDetailActivity.ARG_SELECTED_POKEMON_ID, selectedPokemon.id)
                    }
                    context.startActivity(intent)
                }
            }

            VIEW_TYPE_LOAD_MORE -> {
                holder.itemView.setOnClickListener {
                    loadMoreClickListener?.onLoadMoreClick()
                }
            }

            else -> {
                positionChangeListener?.onReachedBottomList()
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && searchMode) {
            VIEW_TYPE_LOAD_MORE
        } else if (position == itemCount - 1) {
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
        searchMode = false
    }

    @SuppressLint("NotifyDataSetChanged")
    fun searchItems(newItems: List<PokemonData>) {
        pokemonList += newItems
        hasMorePages = newItems.size == PokemonRepository.PAGE_LIMIT
        searchMode = true
        notifyDataSetChanged()
    }

    fun clearItems() {
        pokemonList = emptyList()
        hasMorePages = true
    }

    fun setOnPositionChangeListener(listener: OnPositionChangeListener) {
        positionChangeListener = listener
    }

    fun setOnLoadMoreClickListener(listener: PokemonListActivity) {
        loadMoreClickListener = listener
    }

}