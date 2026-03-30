package com.assignment.pokemon.data.database

import app.cash.sqldelight.db.SqlDriver

/**
 * Platform-specific SQLite driver factory.
 * Android uses AndroidSqliteDriver; iOS uses NativeSqliteDriver.
 */
expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}
