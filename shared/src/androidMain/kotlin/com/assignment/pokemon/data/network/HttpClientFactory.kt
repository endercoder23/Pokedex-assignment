package com.assignment.pokemon.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android

internal actual fun createHttpClient(): HttpClient = HttpClient(Android) {
    engine {
        connectTimeout = 15_000
        socketTimeout = 15_000
    }
}
