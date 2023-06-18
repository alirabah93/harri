package com.smartherd.pokemon.list


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.smartherd.pokemon.R
import com.smartherd.pokemon.databinding.ActivityPokemonListBinding
import com.smartherd.pokemon.list.PokemonRepository.loadPokemonPage
import com.smartherd.pokemon.list.PokemonRepository.searchPokemonName
import com.smartherd.pokemon.models.PokemonData



class PokemonListActivity : AppCompatActivity(), PokemonAdapter.OnPositionChangeListener {

    private lateinit var binding: ActivityPokemonListBinding
    private lateinit var pokemonAdapter: PokemonAdapter
    private var offset = 0
    private var searchName = ""
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        loadPokemon()
        setupSearchEditText()
    }

    override fun onReachedBottomList() {
        if (searchName.isEmpty()){
            loadPokemon()
        }
    }

    private fun setupRecyclerView() {
        val spanCount = resources.getInteger(R.integer.span_count)
        binding.pokemonRecyclerView.layoutManager = GridLayoutManager(this, spanCount)
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
                searchName = s.toString()
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    searchPokemon()
                }, 300)
            }
        })
    }

    private fun loadPokemon(){
        loadPokemonPage(offset, object : PokemonRepository.PokemonCallback{
            override fun onSuccess(pokemons: List<PokemonData>) {
                pokemonAdapter.addItems(pokemons)
                offset = pokemons.size
            }
            override fun onError(error: String) {
                Log.e("Failed Api", "Failed Api with error code: $error")
            }
        }, binding.root)
    }

    private fun searchPokemon(){
        searchPokemonName(searchName, object : PokemonRepository.PokemonCallback{
            override fun onSuccess(pokemons: List<PokemonData>) {
                pokemonAdapter.addItems(pokemons)
            }
            override fun onError(error: String) {
                Log.e("Failed Api", "Failed Api with error code: $error")
            }
        }, binding.root)
    }
}
