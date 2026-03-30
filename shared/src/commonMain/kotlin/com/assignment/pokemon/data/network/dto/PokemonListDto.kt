package com.assignment.pokemon.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonListDto(
    val count: Int,
    val next: String?,
    val results: List<PokemonDto>,
)

@Serializable
data class PokemonDto(
    val name: String,
    val url: String,
) {
    /** Extracts the numeric ID from the PokeAPI URL (e.g. ".../pokemon/1/"). */
    val id: Int
        get() = url.trimEnd('/').split('/').last().toInt()

    val imageUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}
