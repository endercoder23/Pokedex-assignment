package com.assignment.pokemon.data.network

import io.ktor.client.HttpClient

/**
 * Platform-specific HTTP client engine (Android → OkHttp, iOS → Darwin/NSURLSession).
 */
internal expect fun createHttpClient(): HttpClient
