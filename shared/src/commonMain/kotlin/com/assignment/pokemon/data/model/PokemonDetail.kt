package com.assignment.pokemon.data.model

/**
 * Domain model representing the full detail of a Pokemon.
 *
 * @param height in decimetres (divide by 10 for metres)
 * @param weight in hectograms (divide by 10 for kilograms)
 */
data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val height: Int,
    val weight: Int,
    val types: List<String>,
    val stats: List<PokemonStat>,
    val abilities: List<String>,
    val isFavorite: Boolean = false,
)

data class PokemonStat(
    val name: String,
    val value: Int,
)
