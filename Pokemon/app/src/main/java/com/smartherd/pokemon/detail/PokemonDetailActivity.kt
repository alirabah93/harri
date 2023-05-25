package com.smartherd.pokemon.detail

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.smartherd.pokemon.databinding.ActivityPokemonDetailBinding
import com.smartherd.pokemon.models.PokemonData
import com.smartherd.pokemon.models.PokemonTypeColor

class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailBinding
    private lateinit var pokemonDetailAdapter: PokemonDetailAdapter
    private lateinit var selectedPokemon: PokemonData

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedPokemon = intent.getParcelableExtra(ARG_SELECTED_POKEMON)!!

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = selectedPokemon.name

        binding.pokemonWeight.text = selectedPokemon.weight.toString() + " kgs"
        binding.pokemonHeight.text = selectedPokemon.height.toString() + " meters"

        val pokemonType = PokemonTypeColor.fromTypeName(selectedPokemon.typeName)
        val color = pokemonType.getColor(this)
        binding.appBarLayout.setBackgroundColor(color)
        binding.toolbar.setBackgroundColor(color)
        window.statusBarColor = color

        Glide.with(this)
            .load(selectedPokemon.imageUrl)
            .into(binding.pokemonImage)

        val statsList = selectedPokemon.stats
        println("this is statsList: $statsList")

        pokemonDetailAdapter = PokemonDetailAdapter(selectedPokemon.stats)
        binding.pokemonDetailRecyclerView.adapter = pokemonDetailAdapter

    }

    override fun onResume() {
        super.onResume()
        binding.pokemonDetailRecyclerView.adapter = pokemonDetailAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val ARG_SELECTED_POKEMON = "selected_pokemon"
    }
}