package com.assignment.pokemon.presentation.pokemondetail

import com.assignment.pokemon.data.repository.PokemonRepository
import com.assignment.pokemon.presentation.base.BaseViewModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Suppress("UNUSED")
class PokemonDetailViewModel(
    private val pokemonName: String,
    private val repository: PokemonRepository,
) : BaseViewModel() {

    private val _state = MutableStateFlow<PokemonDetailState>(PokemonDetailState.Loading)
    @NativeCoroutinesState
    val state: StateFlow<PokemonDetailState> = _state.asStateFlow()

    init {
        loadDetail()
    }

    // ── Public API ────────────────────────────────────────────────────────────

    fun retry() = loadDetail()

    fun toggleFavorite() {
        val current = _state.value as? PokemonDetailState.Success ?: return
        val pokemon = current.pokemon
        val newValue = !pokemon.isFavorite

        viewModelScope.launch {
            repository.setFavorite(
                id = pokemon.id,
                name = pokemon.name,
                imageUrl = pokemon.imageUrl,
                isFavorite = newValue,
            )
            // Optimistically update the UI
            _state.value = current.copy(pokemon = pokemon.copy(isFavorite = newValue))
        }
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private fun loadDetail() {
        _state.value = PokemonDetailState.Loading
        viewModelScope.launch {
            repository.getPokemonDetail(pokemonName)
                .onSuccess { detail -> _state.value = PokemonDetailState.Success(detail) }
                .onFailure { error -> _state.value = PokemonDetailState.Error(error.message ?: "Unknown error") }
        }
    }
}
