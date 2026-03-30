package com.assignment.pokemon.data.repository

import com.assignment.pokemon.data.database.FavoritesDao
import com.assignment.pokemon.data.model.Pokemon
import com.assignment.pokemon.data.model.PokemonDetail
import com.assignment.pokemon.data.model.PokemonStat
import com.assignment.pokemon.data.network.PokemonApi
import kotlinx.coroutines.flow.Flow

internal class PokemonRepositoryImpl(
    private val api: PokemonApi,
    private val favoritesDao: FavoritesDao,
) : PokemonRepository {

    override suspend fun getPokemonPage(limit: Int, offset: Int): Result<PokemonPage> =
        runCatching {
            val dto = api.getPokemonList(limit = limit, offset = offset)
            PokemonPage(
                pokemon = dto.results.map { Pokemon(id = it.id, name = it.name, imageUrl = it.imageUrl) },
                hasMore = dto.next != null,
            )
        }

    override suspend fun getPokemonDetail(name: String): Result<PokemonDetail> =
        runCatching {
            val dto = api.getPokemonDetail(name)
            val favorite = favoritesDao.isFavorite(dto.id)
            PokemonDetail(
                id = dto.id,
                name = dto.name,
                imageUrl = dto.imageUrl,
                height = dto.height,
                weight = dto.weight,
                types = dto.types.sortedBy { it.slot }.map { it.type.name },
                stats = dto.stats.map { PokemonStat(name = it.stat.name, value = it.baseStat) },
                abilities = dto.abilities
                    .filter { !it.isHidden }
                    .map { it.ability.name },
                isFavorite = favorite,
            )
        }

    override fun observeFavorites(): Flow<List<Pokemon>> =
        favoritesDao.observeAll()

    override suspend fun setFavorite(id: Int, name: String, imageUrl: String, isFavorite: Boolean) {
        if (isFavorite) {
            favoritesDao.insert(id, name, imageUrl)
        } else {
            favoritesDao.delete(id)
        }
    }
}
