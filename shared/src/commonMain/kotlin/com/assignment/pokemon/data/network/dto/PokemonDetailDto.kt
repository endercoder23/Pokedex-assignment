package com.assignment.pokemon.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailDto(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<TypeSlotDto>,
    val stats: List<StatEntryDto>,
    val abilities: List<AbilitySlotDto>,
) {
    val imageUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}

@Serializable
data class TypeSlotDto(
    val slot: Int,
    val type: NamedResourceDto,
)

@Serializable
data class StatEntryDto(
    @SerialName("base_stat") val baseStat: Int,
    val stat: NamedResourceDto,
)

@Serializable
data class AbilitySlotDto(
    @SerialName("is_hidden") val isHidden: Boolean,
    val ability: NamedResourceDto,
)

@Serializable
data class NamedResourceDto(val name: String)
