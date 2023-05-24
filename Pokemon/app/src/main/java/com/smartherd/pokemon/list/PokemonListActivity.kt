package com.smartherd.pokemon.list

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.FrameLayout
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

        pokemonLoader = PokemonLoader(
            this,
            binding.pokemonRecyclerView,
            binding.progressBar,
            binding.emptyErrorContainer
        )
    }

    override fun onResume() {
        super.onResume()
        pokemonLoader.loadPokemon()
    }

    override fun onReachedBottomList() {
        pokemonLoader.increaseOffset()
        pokemonLoader.loadPokemon()
    }

    private fun setupLayoutManager() {
        binding.pokemonRecyclerView.layoutManager = GridLayoutManager(
            this,
            resources.getInteger(R.integer.span_count)
        )
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
                pokemonAdapter.clearItems()
                pokemonLoader.setSearchName(s.toString())
                pokemonLoader.loadPokemon()
            }
        })
    }

}
