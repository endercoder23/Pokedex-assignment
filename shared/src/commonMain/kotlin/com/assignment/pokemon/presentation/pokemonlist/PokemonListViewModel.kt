package com.assignment.pokemon.presentation.pokemonlist

import com.assignment.pokemon.data.model.Pokemon
import com.assignment.pokemon.data.repository.PokemonRepository
import com.assignment.pokemon.presentation.base.BaseViewModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val repository: PokemonRepository,
) : BaseViewModel() {

    private val _state = MutableStateFlow<PokemonListState>(PokemonListState.Loading)
    @NativeCoroutinesState
    val state: StateFlow<PokemonListState> = _state.asStateFlow()

    /** Full unfiltered list kept in memory for client-side search. */
    private val allPokemon = mutableListOf<Pokemon>()
    private var currentOffset = 0
    private var activeSearch = ""

    init {
        loadFirstPage()
    }

    // ── Public API ────────────────────────────────────────────────────────────

    fun refresh() {
        allPokemon.clear()
        currentOffset = 0
        activeSearch = ""
        loadFirstPage()
    }

    fun loadNextPage() {
        val current = _state.value as? PokemonListState.Success ?: return
        if (!current.canLoadMore || current.isLoadingMore) return

        _state.value = current.copy(isLoadingMore = true)
        viewModelScope.launch {
            repository.getPokemonPage(limit = PAGE_SIZE, offset = currentOffset)
                .onSuccess { page ->
                    allPokemon.addAll(page.pokemon)
                    currentOffset += PAGE_SIZE
                    _state.value = PokemonListState.Success(
                        pokemon = filtered(allPokemon, activeSearch),
                        canLoadMore = page.hasMore,
                    )
                }
                .onFailure {
                    // Restore previous state without the loading spinner
                    _state.value = current.copy(isLoadingMore = false)
                }
        }
    }

    /**
     * Filters the already-loaded list by [query] (case-insensitive name match).
     * Does NOT trigger a new network request.
     */
    fun search(query: String) {
        activeSearch = query
        val current = _state.value as? PokemonListState.Success ?: return
        _state.value = current.copy(pokemon = filtered(allPokemon, query))
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private fun loadFirstPage() {
        _state.value = PokemonListState.Loading
        viewModelScope.launch {
            repository.getPokemonPage(limit = PAGE_SIZE, offset = 0)
                .onSuccess { page ->
                    allPokemon.addAll(page.pokemon)
                    currentOffset = PAGE_SIZE
                    _state.value = PokemonListState.Success(
                        pokemon = page.pokemon,
                        canLoadMore = page.hasMore,
                    )
                }
                .onFailure { error ->
                    _state.value = PokemonListState.Error(error.message ?: "Unknown error")
                }
        }
    }

    private fun filtered(list: List<Pokemon>, query: String): List<Pokemon> =
        if (query.isBlank()) list.toList()
        else list.filter { it.name.contains(query, ignoreCase = true) }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
