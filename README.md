# PokéDex — KMP Assignment

A Kotlin Multiplatform assignment using the [PokéAPI](https://pokeapi.co/).

## Repository layout

```
.
├── shared/          ← KMP module (provided — do not modify)
│   └── src/
│       ├── commonMain/   ← business logic, models, ViewModels
│       ├── androidMain/  ← Android engine & SQLite driver
│       └── iosMain/      ← Darwin engine & Native SQLite driver
│
├── androidApp/      ← Android candidate works here
└── iosApp/          ← iOS candidate works here
```

## What the shared module exposes

### Domain models (`data/model`)

| Type | Description |
|------|-------------|
| `Pokemon` | `id`, `name`, `imageUrl` |
| `PokemonDetail` | Full detail including `types`, `stats`, `abilities`, `isFavorite` |
| `PokemonStat` | `name` + `value` |

### ViewModels (`presentation/`)

#### `PokemonListViewModel(repository)`
| Member | Type | Description |
|--------|------|-------------|
| `state` | `StateFlow<PokemonListState>` | `Loading / Success / Error` |
| `loadNextPage()` | fun | Appends next 20 Pokémon |
| `search(query)` | fun | Client-side name filter |
| `refresh()` | fun | Resets and reloads from page 1 |

#### `PokemonDetailViewModel(name, repository)`
| Member | Type | Description |
|--------|------|-------------|
| `state` | `StateFlow<PokemonDetailState>` | `Loading / Success / Error` |
| `toggleFavorite()` | fun | Adds/removes from local DB |
| `retry()` | fun | Re-fetches after an error |

#### `FavoritesViewModel(repository)`
| Member | Type | Description |
|--------|------|-------------|
| `favorites` | `StateFlow<List<Pokemon>>` | Live stream from SQLite |

### DI entry point

```kotlin
// Kotlin / Android
initKoin(driverFactory = DatabaseDriverFactory(context), enableNetworkLogs = true)

// Swift / iOS
ModulesKt.doInitKoin(driverFactory: DatabaseDriverFactory(), enableNetworkLogs: true)
```

## Building the shared module

```bash
# Android AAR (consumed via Gradle project dependency)
./gradlew :shared:assembleRelease

# iOS XCFramework
./gradlew :shared:assembleSharedXCFramework
```

## Assignment requirements

See:
- [`androidApp/README.md`](androidApp/README.md) — Android task
- [`iosApp/README.md`](iosApp/README.md) — iOS task
