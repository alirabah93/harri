package com.smartherd.pokemon.list

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smartherd.pokemon.R
import com.smartherd.pokemon.databinding.ActivityPokemonListBinding

class PokemonListActivity : AppCompatActivity(), PokemonAdapter.OnPositionChangeListener {

    private lateinit var binding: ActivityPokemonListBinding
    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var pokemonLoader: PokemonLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLayoutManager()
        setupAdapter()
        setupSearchEditText()

        pokemonLoader = PokemonLoader(this, binding.pokemonRecyclerView, binding.progressBar)
    }

    override fun onResume() {
        super.onResume()
        showProgressBar()
        pokemonLoader.loadPokemon()
    }

    override fun onReachedBottomList() {
        showProgressBar()
        pokemonLoader.increaseOffset()
        pokemonLoader.loadPokemon()
    }

    private fun setupLayoutManager() {
        val orientation = resources.configuration.orientation
        val spanCount = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 2
        binding.pokemonRecyclerView.layoutManager = GridLayoutManager(this, spanCount)
    }

    private fun setupAdapter() {
        pokemonAdapter = PokemonAdapter(emptyList())
        binding.pokemonRecyclerView.adapter = pokemonAdapter
        pokemonAdapter.setOnPositionChangeListener(this)
    }

    private fun setupSearchEditText() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed for this implementation
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed for this implementation
            }

            override fun afterTextChanged(s: Editable?) {
                pokemonLoader.setSearchName(s.toString())
                pokemonLoader.loadPokemon()
            }
        })
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }
}
