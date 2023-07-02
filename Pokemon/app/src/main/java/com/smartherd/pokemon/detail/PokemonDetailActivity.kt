package com.smartherd.pokemon.detail

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.smartherd.pokemon.R
import com.smartherd.pokemon.databinding.ActivityPokemonDetailBinding
import com.smartherd.pokemon.models.PokemonStat
import com.smartherd.pokemon.models.PokemonTypeColor

class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailBinding
    private lateinit var pokemonDetailAdapter: PokemonDetailAdapter
    private lateinit var viewModel: DetailViewModel

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pokemon_detail)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val selectedPokemonId = intent.getIntExtra(ARG_SELECTED_POKEMON_ID, 0)

        initializeViewModel(selectedPokemonId)
        observePokemonDetails()


    }

    private fun initializeViewModel(id: Int) {
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        viewModel.getPokemonDetails(id)
        binding.detailViewModel = viewModel
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun observePokemonDetails() {
        viewModel.selectedPokemon.observe(this) { pokemon ->
            supportActionBar?.title = pokemon.name
            setPokemonTypeColor(pokemon.typeName)
            loadImage(pokemon.imageUrl)
            setupRecyclerView(pokemon.stats)
        }
        viewModel.error.observe(this) { error ->
            Log.e("PokemonListActivity", "API Error: $error")
        }
    }

    private fun loadImage(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .into(binding.pokemonImage)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setPokemonTypeColor(typeName: String) {
        val pokemonType = PokemonTypeColor.fromTypeName(typeName)
        val color = pokemonType.getColor(this)
        binding.appBarLayout.setBackgroundColor(color)
        binding.toolbar.setBackgroundColor(color)
        window.statusBarColor = color
    }


    private fun setupRecyclerView(statsList: List<PokemonStat>) {
        val spanCount = resources.getInteger(R.integer.span_count)
        binding.pokemonDetailRecyclerView.layoutManager =
            GridLayoutManager(this, spanCount)
        pokemonDetailAdapter = PokemonDetailAdapter(statsList)
        binding.pokemonDetailRecyclerView.adapter = pokemonDetailAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val ARG_SELECTED_POKEMON_ID = "selected_pokemon_id"
    }
}
