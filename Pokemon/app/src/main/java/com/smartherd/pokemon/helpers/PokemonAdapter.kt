package com.smartherd.pokemon.helpers

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smartherd.pokemon.R
import com.smartherd.pokemon.activities.PokemonDetailActivity
import com.smartherd.pokemon.models.Pokemon
import com.smartherd.pokemon.models.PokemonType

class PokemonAdapter(private val pokemonList: List<Pokemon>) :
    RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.pokemon = pokemonList[position]
        holder.pokemonName.text = pokemonList[position].name

        val pokemonImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${position + 1}.png"
        Glide.with(holder.itemView.context)
            .load(pokemonImageUrl)
            .into(holder.pokemonImage)

        val context = holder.itemView.context
//        val pokemonType = getPokemonType(position)
//        setPokemonTypeColor(context, pokemonType.toString(), holder.pokemonImage)

        holder.itemView.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context, PokemonDetailActivity::class.java)
//            intent.putExtra(PokemonDetailActivity.ARG_ITEM_ID, holder.pokemon!!.id)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val pokemonImage : ImageView = itemView.findViewById(R.id.pokemon_image)
        val pokemonName: TextView = itemView.findViewById(R.id.pokemon_name)
        var pokemon: Pokemon? = null

    }

//    private fun getPokemonType(position: Int): List<PokemonType> {
//        return pokemonList[position].types
//    }

//    private fun setPokemonTypeColor(context: Context, pokemonType: String, imageView: ImageView) {
//        val color: Int = when (pokemonType) {
//            "fire" -> ContextCompat.getColor(context, R.color.fire)
//            "water" -> ContextCompat.getColor(context, R.color.water)
//            "grass" -> ContextCompat.getColor(context, R.color.grass)
//            "electric" -> ContextCompat.getColor(context, R.color.electric)
//            "ice" -> ContextCompat.getColor(context, R.color.ice)
//            "fighting" -> ContextCompat.getColor(context, R.color.fighting)
//            "poison" -> ContextCompat.getColor(context, R.color.poison)
//            "ground" -> ContextCompat.getColor(context, R.color.ground)
//            "flying" -> ContextCompat.getColor(context, R.color.flying)
//            "psychic" -> ContextCompat.getColor(context, R.color.psychic)
//            "bug" -> ContextCompat.getColor(context, R.color.bug)
//            "rock" -> ContextCompat.getColor(context, R.color.rock)
//            "ghost" -> ContextCompat.getColor(context, R.color.ghost)
//            "dragon" -> ContextCompat.getColor(context, R.color.dragon)
//            "dark" -> ContextCompat.getColor(context, R.color.dark)
//            "steel" -> ContextCompat.getColor(context, R.color.steel)
//            "fairy" -> ContextCompat.getColor(context, R.color.fairy)
//            else -> ContextCompat.getColor(context, R.color.normal)
//        }
//        imageView.setColorFilter(color)
//    }
}