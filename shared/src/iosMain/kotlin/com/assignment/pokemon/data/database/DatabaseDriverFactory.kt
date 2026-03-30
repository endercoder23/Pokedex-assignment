package com.assignment.pokemon.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.assignment.pokemon.database.PokemonDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver =
        NativeSqliteDriver(PokemonDatabase.Schema, "pokemon.db")
}
