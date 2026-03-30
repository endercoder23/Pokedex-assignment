package com.assignment.pokemon.data.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.assignment.pokemon.data.model.Pokemon
import com.assignment.pokemon.database.PokemonDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class FavoritesDao(private val db: PokemonDatabase) {

    fun observeAll(): Flow<List<Pokemon>> =
        db.favoritePokemonQueries
            .getAllFavorites()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { rows -> rows.map { Pokemon(id = it.id.toInt(), name = it.name, imageUrl = it.imageUrl) } }

    suspend fun insert(id: Int, name: String, imageUrl: String) =
        withContext(Dispatchers.Default) {
            db.favoritePokemonQueries.insertFavorite(id.toLong(), name, imageUrl)
        }

    suspend fun delete(id: Int) =
        withContext(Dispatchers.Default) {
            db.favoritePokemonQueries.deleteFavorite(id.toLong())
        }

    suspend fun isFavorite(id: Int): Boolean =
        withContext(Dispatchers.Default) {
            db.favoritePokemonQueries.isFavorite(id.toLong()).executeAsOne()
        }
}
