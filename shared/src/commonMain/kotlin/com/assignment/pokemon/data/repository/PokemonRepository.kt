package com.assignment.pokemon.data.repository

import com.assignment.pokemon.data.model.Pokemon
import com.assignment.pokemon.data.model.PokemonDetail
import kotlinx.coroutines.flow.Flow

data class PokemonPage(
    val pokemon: List<Pokemon>,
    val hasMore: Boolean,
)

interface PokemonRepository {

    /** Returns one page of Pokemon from the list endpoint. */
    suspend fun getPokemonPage(limit: Int, offset: Int): Result<PokemonPage>

    /** Returns full detail for a given Pokemon by name or ID string. */
    suspend fun getPokemonDetail(name: String): Result<PokemonDetail>

    /** Live stream of favorited Pokemon backed by the local database. */
    fun observeFavorites(): Flow<List<Pokemon>>

    /** Adds or removes a Pokemon from the favorites database. */
    suspend fun setFavorite(id: Int, name: String, imageUrl: String, isFavorite: Boolean)
}
