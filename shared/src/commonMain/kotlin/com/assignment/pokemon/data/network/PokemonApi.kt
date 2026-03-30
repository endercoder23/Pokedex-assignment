package com.assignment.pokemon.data.network

import com.assignment.pokemon.data.network.dto.PokemonDetailDto
import com.assignment.pokemon.data.network.dto.PokemonListDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal class PokemonApi(private val httpClient: HttpClient) {

    suspend fun getPokemonList(limit: Int, offset: Int): PokemonListDto =
        httpClient.get("$BASE_URL/pokemon") {
            parameter("limit", limit)
            parameter("offset", offset)
        }.body()

    suspend fun getPokemonDetail(name: String): PokemonDetailDto =
        httpClient.get("$BASE_URL/pokemon/$name").body()

    companion object {
        private const val BASE_URL = "https://pokeapi.co/api/v2"

        fun build(enableLogging: Boolean): PokemonApi {
            val client = createHttpClient().config {
                install(ContentNegotiation) {
                    json(Json { ignoreUnknownKeys = true })
                }
                if (enableLogging) {
                    install(Logging) { level = LogLevel.BODY }
                }
            }
            return PokemonApi(client)
        }
    }
}
