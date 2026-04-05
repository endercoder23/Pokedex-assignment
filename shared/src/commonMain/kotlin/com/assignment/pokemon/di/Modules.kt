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

// Store the repository instance so it can be accessed from Swift
private var _repository: PokemonRepository? = null

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
    val koinApp = startKoin {
        appDeclaration()
        modules(
            networkModule(enableNetworkLogs),
            databaseModule(driverFactory),
            repositoryModule,
            viewModelModule,  // Register ViewModels (PokemonListViewModel, etc)
        )
    }
    
    // Eager initialization: access repository to ensure _repository is set
    try {
        val repo: PokemonRepository = koinApp.koin.get()
        println("✅ Repository initialized: ${repo::class.simpleName}")
    } catch (e: Exception) {
        println("❌ ERROR: Repository initialization failed: ${e.message}")
        e.printStackTrace()
    }
}

/**
 * Get the PokemonRepository instance from Koin.
 * Call this after initKoin() has been called.
 */
@Throws(IllegalStateException::class)
fun getRepository(): PokemonRepository {
    return _repository ?: throw IllegalStateException("Repository not initialized. Call initKoin() first.")
}

private fun networkModule(enableLogging: Boolean) = module {
    single { PokemonApi.build(enableLogging) }
}

private fun databaseModule(driverFactory: DatabaseDriverFactory) = module {
    single { PokemonDatabase(driverFactory.createDriver()) }
    single { FavoritesDao(get()) }
}

private val repositoryModule = module {
    single<PokemonRepository> { 
        PokemonRepositoryImpl(get(), get()).also { repo ->
            _repository = repo  // Capture the repository instance
        }
    }
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
