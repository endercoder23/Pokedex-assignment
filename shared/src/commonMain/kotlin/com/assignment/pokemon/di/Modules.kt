package com.assignment.pokemon.di

import com.assignment.pokemon.data.database.DatabaseDriverFactory
import com.assignment.pokemon.data.database.FavoritesDao
import com.assignment.pokemon.data.network.PokemonApi
import com.assignment.pokemon.data.repository.PokemonRepository
import com.assignment.pokemon.data.repository.PokemonRepositoryImpl
import com.assignment.pokemon.database.PokemonDatabase
import com.assignment.pokemon.presentation.favorites.FavoritesViewModel
import com.assignment.pokemon.presentation.pokemondetail.PokemonDetailViewModel
import com.assignment.pokemon.presentation.pokemonlist.PokemonListViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Call this once at app startup (Application.onCreate on Android, or in the
 * iOS app delegate / SwiftUI @main init).
 *
 * @param driverFactory  Platform-specific [DatabaseDriverFactory].
 * @param enableNetworkLogs  Set true for debug builds to log HTTP traffic.
 */
fun initKoin(
    driverFactory: DatabaseDriverFactory,
    enableNetworkLogs: Boolean = false,
    appDeclaration: KoinApplication.() -> Unit = {},
) {
    startKoin {
        appDeclaration()
        modules(
            networkModule(enableNetworkLogs),
            databaseModule(driverFactory),
            repositoryModule,
        )
    }
}

private fun networkModule(enableLogging: Boolean) = module {
    single { PokemonApi.build(enableLogging) }
}

private fun databaseModule(driverFactory: DatabaseDriverFactory) = module {
    single { PokemonDatabase(driverFactory.createDriver()) }
    single { FavoritesDao(get()) }
}

private val repositoryModule = module {
    single<PokemonRepository> { PokemonRepositoryImpl(get(), get()) }
}

/**
 * Convenience Koin extensions — use these in the platform DI or directly
 * from Activity / UIViewController to obtain ViewModels.
 *
 * Example (Android):
 *   val vm: PokemonListViewModel = KoinPlatform.getKoin().get()
 *
 * Example (iOS):
 *   let vm = KoinHelper().getPokemonListViewModel()
 */
val viewModelModule = module {
    factoryOf(::PokemonListViewModel)
    factoryOf(::FavoritesViewModel)
    // PokemonDetailViewModel requires pokemonName — pass it via parameters:
    // get { parametersOf("bulbasaur") }
    factory { (name: String) -> PokemonDetailViewModel(name, get()) }
}
