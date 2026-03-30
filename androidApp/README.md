# Android Assignment

## Your Task

Build the Android UI for the PokéDex app by integrating the `:shared` KMP module.

## Setup

1. Add the shared module dependency to your `app/build.gradle.kts`:
   ```kotlin
   implementation(project(":shared"))
   ```

2. Initialise Koin in your `Application` class:
   ```kotlin
   class App : Application() {
       override fun onCreate() {
           super.onCreate()
           initKoin(
               driverFactory = DatabaseDriverFactory(this),
               enableNetworkLogs = BuildConfig.DEBUG,
               appDeclaration = { androidContext(this@App) }
           )
       }
   }
   ```

## Consuming a ViewModel

Wrap the KMP ViewModel in an Android ViewModel to respect the lifecycle:

```kotlin
class PokemonListAndroidViewModel : ViewModel() {
    // Get the repository from Koin and pass it to the KMP ViewModel
    private val repository: PokemonRepository = KoinPlatform.getKoin().get()
    val kmp = PokemonListViewModel(repository)

    override fun onCleared() {
        kmp.onCleared()
    }
}
```

Observe state in a Composable:
```kotlin
val state by androidViewModel.kmp.state.collectAsStateWithLifecycle()
```

## Screens to build

| Screen | ViewModel | Notes |
|--------|-----------|-------|
| Pokemon List | `PokemonListViewModel` | Grid, search bar, pagination |
| Pokemon Detail | `PokemonDetailViewModel(name, repo)` | Stats, types, favourite button |
| Favourites | `FavoritesViewModel` | Observes live DB stream |

## Tech Stack (Suggested)
- Jetpack Compose for UI
- Navigation Compose for routing
- Coil for image loading
- `collectAsStateWithLifecycle` for StateFlow collection
