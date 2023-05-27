package com.smartherd.pokemon.detail

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.smartherd.pokemon.R
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

        val spanCount = resources.getInteger(R.integer.span_count)
        binding.pokemonDetailRecyclerView.layoutManager = GridLayoutManager(this, spanCount)
        pokemonDetailAdapter = PokemonDetailAdapter(emptyList())
        binding.pokemonDetailRecyclerView.adapter = pokemonDetailAdapter

        val statsList = selectedPokemon.stats
        pokemonDetailAdapter.setStats(statsList)

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val ARG_SELECTED_POKEMON = "selected_pokemon"
    }
}