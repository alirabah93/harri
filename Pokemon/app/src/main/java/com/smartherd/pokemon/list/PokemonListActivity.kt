package com.smartherd.pokemon.list


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.smartherd.pokemon.R
import com.smartherd.pokemon.data.PokemonRepository
import com.smartherd.pokemon.databinding.ActivityPokemonListBinding
import com.smartherd.pokemon.detail.PokemonDetailActivity


class PokemonListActivity :
    AppCompatActivity(),
    OnPositionChangeListener,
    OnLoadMoreClickListener {

    private lateinit var binding: ActivityPokemonListBinding
    private lateinit var viewModel: PokemonListViewModel
    private lateinit var pokemonListAdapter: PokemonListAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val handler = Handler()
    private var searchName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pokemon_list)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this)[PokemonListViewModel::class.java]
        binding.viewModel = viewModel

        setupRecyclerView()
        setupSwipeRefreshLayout()
        setupSearchEditText()

        viewModel.loadPokemon()

        viewModel.pokemonList.observe(this) { pokemons ->
            if (searchName.isNotEmpty()) {
                pokemonListAdapter.searchItems(pokemons)
            } else {
                pokemonListAdapter.addItems(pokemons)
            }
            pokemonListAdapter.notifyItemRangeInserted(viewModel.offset.value!!, pokemons.size)
        }

        viewModel.error.observe(this) { error ->
            Log.e("PokemonListActivity", "API Error: $error")
        }

    }

    override fun onReachedBottomList() {
        if (searchName.isEmpty()) {
            viewModel.loadPokemon()
        }
    }

    override fun onLoadMoreClick() {
        viewModel.searchPokemon(searchName)
    }

    private fun setupRecyclerView() {
        val spanCount = resources.getInteger(R.integer.span_count)
        val gridLayoutManager = GridLayoutManager(this, spanCount)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == pokemonListAdapter.itemCount - 1) {
                    spanCount
                } else {
                    1
                }
            }
        }
        binding.pokemonRecyclerView.layoutManager = gridLayoutManager

        pokemonListAdapter = PokemonListAdapter(emptyList()) {
            val intent = Intent(this, PokemonDetailActivity::class.java).apply {
                putExtra(PokemonDetailActivity.ARG_SELECTED_POKEMON_ID, it.id)
            }
            startActivity(intent)
        }
        binding.pokemonRecyclerView.adapter = pokemonListAdapter
        pokemonListAdapter.setOnPositionChangeListener(this)
        pokemonListAdapter.setOnLoadMoreClickListener(this)
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
                    val count = pokemonListAdapter.itemCount
                    pokemonListAdapter.clearItems()
                    pokemonListAdapter.notifyItemRangeRemoved(0, count)
                    if (searchName.isEmpty()) {
                        viewModel.loadPokemon()
                        swipeRefreshLayout.isEnabled = true
                    } else {
                        viewModel.searchPokemon(searchName)
                        swipeRefreshLayout.isEnabled = false
                    }
                    pokemonListAdapter.notifyItemRangeInserted(
                        viewModel.offset.value!!,
                        pokemonListAdapter.itemCount
                    )
                }, 300)
            }
        })
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            if (searchName.isEmpty()) {
                val count = pokemonListAdapter.itemCount
                pokemonListAdapter.clearItems()
                pokemonListAdapter.notifyItemRangeRemoved(0, count)
                viewModel.setOffset(0)
                PokemonRepository.clearAllPokemons()
                viewModel.loadPokemon()
            }
            swipeRefreshLayout.isRefreshing = false
        }
    }


}
