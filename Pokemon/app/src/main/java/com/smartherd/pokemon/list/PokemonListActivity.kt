package com.smartherd.pokemon.list


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.smartherd.pokemon.R
import com.smartherd.pokemon.databinding.ActivityPokemonListBinding
import com.smartherd.pokemon.models.PokemonData


class PokemonListActivity : AppCompatActivity(), PokemonAdapter.OnPositionChangeListener {

    private lateinit var binding: ActivityPokemonListBinding
    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var offset = 0
    private var searchLimiter = 10
    private var searchName = ""
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        setupSwipeRefreshLayout()
        loadPokemon()
        setupSearchEditText()
    }

    override fun onReachedBottomList() {
        if (searchName.isEmpty()) {
            loadPokemon()
        } else {
            searchPokemon()
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
                handler.postDelayed({
                    offset = 0
                    pokemonAdapter.clearItems()
                    if (searchName.isNotEmpty()) {
                        searchPokemon()
                    } else {
                        loadPokemon()
                    }
                }, 300)
            }
        })
    }

    private fun loadPokemon() {
        PokemonRepository.loadPokemonPage(offset, object : PokemonCallback {
            override fun onSuccess(pokemons: List<PokemonData>) {
                pokemonAdapter.addItems(pokemons as MutableList<PokemonData>)
                if (pokemons.size > 20 && offset == 0) {
                    offset = pokemons.size
                } else {
                    offset += 20
                }
            }

            override fun onError(error: String) {
                Log.e("Failed Api", "Failed Api with error code: $error")
            }
        }, binding.root)
    }

    private fun searchPokemon() {
        PokemonRepository.searchPokemonName(offset, searchName, object : PokemonCallback {
            override fun onSuccess(pokemons: List<PokemonData>) {
                pokemonAdapter.addItems(pokemons as MutableList<PokemonData>)
                offset = pokemonAdapter.itemCount
            }

            override fun onError(error: String) {
                Log.e("Failed Api", "Failed Api with error code: $error")
            }
        }, binding.root)
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            refreshPokemonList()
        }
    }

    private fun refreshPokemonList() {
        pokemonAdapter.clearItems()
        offset = 0
        if (searchName.isEmpty()) {
            PokemonRepository.clearAllPokemons()
            loadPokemon()
        } else {
            searchPokemon()
        }
        swipeRefreshLayout.isRefreshing = false
    }
}
