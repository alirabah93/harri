package com.smartherd.pokemon.list


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.smartherd.pokemon.R
import com.smartherd.pokemon.data.PokemonRepository
import com.smartherd.pokemon.databinding.ActivityPokemonListBinding


class PokemonListActivity : AppCompatActivity(), OnPositionChangeListener {

    private lateinit var binding: ActivityPokemonListBinding
    private lateinit var viewModel: ListViewModel
    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val handler = Handler()
    private var searchName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pokemon_list)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ListViewModel::class.java]

        setupRecyclerView()
        setupSwipeRefreshLayout()
        setupSearchEditText()

        viewModel.loadPokemon()

        viewModel.pokemonList.observe(this) { pokemons ->
            pokemonAdapter.addItems(pokemons)
            pokemonAdapter.notifyItemRangeInserted(viewModel.offset.value!!, pokemons.size)
        }

        viewModel.error.observe(this) { error ->
            Log.e("PokemonListActivity", "API Error: $error")
        }

    }

    override fun onReachedBottomList() {
        if (searchName.isEmpty()) {
            viewModel.loadPokemon()
        }
//        else {
//            viewModel.searchPokemon(searchName)
//        }
    }

    private fun setupRecyclerView() {
        val spanCount = resources.getInteger(R.integer.span_count)
        val gridLayoutManager = GridLayoutManager(this, spanCount)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == pokemonAdapter.itemCount - 1) {
                    spanCount
                } else {
                    1
                }
            }
        }
        binding.pokemonRecyclerView.layoutManager = gridLayoutManager

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
                    viewModel.setOffset(0)
                    pokemonAdapter.clearItems()
                    if (searchName.isEmpty()) {
                        viewModel.loadPokemon()
                    } else {
                        viewModel.searchPokemon(searchName)
                    }
                    pokemonAdapter.notifyItemRangeRemoved(0, pokemonAdapter.itemCount)
                }, 300)
            }
        })
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            if (searchName.isEmpty()) {
                swipeRefreshLayout.isEnabled = true
                swipeRefreshLayout.visibility = View.VISIBLE
                val count = pokemonAdapter.itemCount
                pokemonAdapter.clearItems()
                pokemonAdapter.notifyItemRangeRemoved(0, count)
                viewModel.setOffset(0)
                PokemonRepository.clearAllPokemons()
                viewModel.loadPokemon()
            } else {
                swipeRefreshLayout.isEnabled = false
                swipeRefreshLayout.visibility = View.GONE
            }
            swipeRefreshLayout.isRefreshing = false
        }
    }
}
