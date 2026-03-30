package com.assignment.pokemon.presentation.pokemondetail

import com.assignment.pokemon.data.model.PokemonDetail

sealed class PokemonDetailState {
    data object Loading : PokemonDetailState()
    data class Success(val pokemon: PokemonDetail) : PokemonDetailState()
    data class Error(val message: String) : PokemonDetailState()
}
