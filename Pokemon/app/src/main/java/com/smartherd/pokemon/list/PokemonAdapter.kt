package com.smartherd.pokemon.list

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smartherd.pokemon.R
import com.smartherd.pokemon.detail.PokemonDetailActivity
import com.smartherd.pokemon.models.Pokemon

class PokemonAdapter(private var pokemonList: List<Pokemon>) :
    RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    private var positionChangeListener: OnPositionChangeListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindPokemon(pokemonList[position])
        holder.itemView.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context, PokemonDetailActivity::class.java).apply {
                putExtra(PokemonDetailActivity.ARG_ITEM_NAME, holder.pokemon?.name)
            }
            context.startActivity(intent)
        }

        if (position == pokemonList.size - 1) {
            positionChangeListener?.onReachedBottomList()
        }
    }

    override fun getItemCount(): Int = pokemonList.size

    fun addItems(newItems: List<Pokemon>) {
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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pokemonImage: ImageView = itemView.findViewById(R.id.pokemon_image)
        private val pokemonName: TextView = itemView.findViewById(R.id.pokemon_name)
        private val context: Context = itemView.context
        var pokemon: Pokemon? = null

        fun bindPokemon(pokemon: Pokemon) {
            pokemonName.text = pokemon.name
            val pokemonId = extractPokemonId(pokemon.url)
            val pokemonImageUrl =
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"

            Glide.with(context)
                .load(pokemonImageUrl)
                .into(pokemonImage)

            PokemonTypeColorMapper(context).setPokemonTypeColor(pokemonId, pokemonImage)
        }

        private fun extractPokemonId(pokemonUrl: String): String? {
            val regex = Regex("""/pokemon/(\d+)/""")
            return regex.find(pokemonUrl)?.groupValues?.get(1)
        }
    }
}
