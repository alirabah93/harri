package com.smartherd.pokemon.detail

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.smartherd.pokemon.R
import com.smartherd.pokemon.databinding.ActivityPokemonDetailBinding
import com.smartherd.pokemon.list.PokemonRepository
import com.smartherd.pokemon.models.PokemonData
import com.smartherd.pokemon.models.PokemonTypeColor

class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailBinding
    private lateinit var pokemonDetailAdapter: PokemonDetailAdapter

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val selectedPokemonId = intent.getIntExtra(ARG_SELECTED_POKEMON_ID, 0)
        loadPokemonDetails(selectedPokemonId)

    }

    private fun loadPokemonDetails(id: Int){
        PokemonRepository.loadPokemonDetails(id, object : PokemonDetailsCallback {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("SetTextI18n")
            override fun onSuccess(selectedPokemon: PokemonData) {
                supportActionBar?.title = selectedPokemon.name

                binding.pokemonWeight.text = selectedPokemon.weight.toString() + " kgs"
                binding.pokemonHeight.text = selectedPokemon.height.toString() + " meters"

                val pokemonType = PokemonTypeColor.fromTypeName(selectedPokemon.typeName)
                val color = pokemonType.getColor(this@PokemonDetailActivity)
                binding.appBarLayout.setBackgroundColor(color)
                binding.toolbar.setBackgroundColor(color)
                window.statusBarColor = color

                Glide.with(this@PokemonDetailActivity)
                    .load(selectedPokemon.imageUrl)
                    .into(binding.pokemonImage)

                val spanCount = resources.getInteger(R.integer.span_count)
                binding.pokemonDetailRecyclerView.layoutManager = GridLayoutManager(this@PokemonDetailActivity, spanCount)
                pokemonDetailAdapter = PokemonDetailAdapter(emptyList())
                binding.pokemonDetailRecyclerView.adapter = pokemonDetailAdapter

                val statsList = selectedPokemon.stats
                pokemonDetailAdapter.setStats(statsList)
            }

            override fun onError(error: String) {
                Log.e("Failed Api", "Failed Api with error code: $error")
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val ARG_SELECTED_POKEMON_ID = "selected_pokemon_id"
    }
}