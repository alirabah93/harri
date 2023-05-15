package com.smartherd.pokemon.helpers


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
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
import com.smartherd.pokemon.activities.PokemonListActivity
import com.smartherd.pokemon.models.Pokemon
import com.smartherd.pokemon.models.PokemonListResponse
import com.smartherd.pokemon.models.PokemonTypeSlot
import com.smartherd.pokemon.services.PokemonService
import com.smartherd.pokemon.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonAdapter(
    private var pokemonList: List<Pokemon>
) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the list item layout
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind the Pokemon data to the ViewHolder
        holder.bindPokemon(pokemonList[position])

        // Set click listener for opening Pokemon detail activity
        holder.itemView.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context, PokemonDetailActivity::class.java)
            intent.putExtra(PokemonDetailActivity.ARG_ITEM_NAME, holder.pokemon?.name)
            context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        val size = pokemonList.size
        return pokemonList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pokemonImage: ImageView = itemView.findViewById(R.id.pokemon_image)
        private val pokemonName: TextView = itemView.findViewById(R.id.pokemon_name)
        private val pokemonType: TextView = itemView.findViewById(R.id.pokemon_type)
        var pokemon: Pokemon? = null
        private var context: Context = itemView.context
        fun bindPokemon(pokemon: Pokemon) {

            this.pokemon = pokemon
            pokemonName.text = pokemon.name

            val pokemonUrl = pokemon.url
            val regex = Regex("""/pokemon/(\d+)/""")
            val matchResult = regex.find(pokemonUrl)
            val pokemonId = matchResult?.groupValues?.get(1)

            val pokemonImageUrl =
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"
            Glide.with(itemView.context)
                .load(pokemonImageUrl)
                .into(pokemonImage)

            val pokemonService: PokemonService =
                ServiceBuilder.buildService(PokemonService::class.java)
            val requestCall: Call<PokemonTypeSlot> = pokemonService.getPokemonType(pokemonId)

            if (pokemonId != null) {
                // Call the API endpoint to get the types of the Pokemon with the given ID
                requestCall.enqueue(object : Callback<PokemonTypeSlot> {
                    override fun onResponse(
                        call: Call<PokemonTypeSlot>,
                        response: Response<PokemonTypeSlot>
                    ) {
                        if (response.isSuccessful) {
                            val pokemonTypes = response.body()?.types
                            if (!pokemonTypes.isNullOrEmpty()) {
                                pokemonType.text = pokemonTypes[0].type.name
                                val pokemonTypeName = pokemonTypes[0].type.name
                                setPokemonTypeColor(context, pokemonTypeName, pokemonImage)
                            }
                        }
                    }

                    override fun onFailure(call: Call<PokemonTypeSlot>, t: Throwable) {
                        Log.e("Failed Api", "Failed Api with error code: " + t.message)
                    }
                })
            }
        }

        private fun setPokemonTypeColor(
            context: Context,
            pokemonTypeName: String,
            imageView: ImageView
        ) {
            val color: Int = when (pokemonTypeName) {
                "fire" -> ContextCompat.getColor(context, R.color.fire)
                "water" -> ContextCompat.getColor(context, R.color.water)
                "grass" -> ContextCompat.getColor(context, R.color.grass)
                "electric" -> ContextCompat.getColor(context, R.color.electric)
                "ice" -> ContextCompat.getColor(context, R.color.ice)
                "fighting" -> ContextCompat.getColor(context, R.color.fighting)
                "poison" -> ContextCompat.getColor(context, R.color.poison)
                "ground" -> ContextCompat.getColor(context, R.color.ground)
                "flying" -> ContextCompat.getColor(context, R.color.flying)
                "psychic" -> ContextCompat.getColor(context, R.color.psychic)
                "bug" -> ContextCompat.getColor(context, R.color.bug)
                "rock" -> ContextCompat.getColor(context, R.color.rock)
                "ghost" -> ContextCompat.getColor(context, R.color.ghost)
                "dragon" -> ContextCompat.getColor(context, R.color.dragon)
                "dark" -> ContextCompat.getColor(context, R.color.dark)
                "steel" -> ContextCompat.getColor(context, R.color.steel)
                "fairy" -> ContextCompat.getColor(context, R.color.fairy)
                else -> ContextCompat.getColor(context, R.color.normal)
            }
            imageView.setBackgroundColor(color)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(newItems: List<Pokemon>) {
        val currentList = ArrayList(pokemonList)
        currentList.addAll(newItems)
        pokemonList = currentList
        notifyDataSetChanged()
    }

}