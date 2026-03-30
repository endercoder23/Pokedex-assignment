package com.assignment.pokemon.presentation.pokemonlist

import com.assignment.pokemon.data.model.Pokemon

sealed class PokemonListState {
    data object Loading : PokemonListState()

    data class Success(
        val pokemon: List<Pokemon>,
        /** True while the next page is being fetched. */
        val isLoadingMore: Boolean = false,
        val canLoadMore: Boolean = true,
    ) : PokemonListState()

    data class Error(val message: String) : PokemonListState()
}
