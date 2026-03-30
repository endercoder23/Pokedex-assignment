# iOS Assignment

## Your Task

Build the iOS UI for the PokéDex app by integrating the `shared` KMP XCFramework.

## Setup

### Option A — CocoaPods (recommended for assignment)

1. Create a `Podfile` in `iosApp/`:
   ```ruby
   use_frameworks!
   platform :ios, '16.0'

   target 'iosApp' do
     pod 'shared', :path => '../shared'
   end
   ```

2. Run `pod install` and open the `.xcworkspace`.

3. The Gradle `syncFramework` task runs automatically before each Xcode build.

### Option B — XCFramework (manual)

Build the XCFramework once from the repo root:
```bash
./gradlew :shared:assembleSharedXCFramework
```
Then drag `shared/build/XCFrameworks/release/shared.xcframework` into your Xcode project.

## Initialising Koin

In your `@main` App struct or `AppDelegate`:
```swift
import shared

@main
struct iOSApp: App {
    init() {
        ModulesKt.doInitKoin(
            driverFactory: DatabaseDriverFactory(),
            enableNetworkLogs: true
        )
    }
    var body: some Scene {
        WindowGroup { ContentView() }
    }
}
```

## Observing StateFlow from SwiftUI

KMP `StateFlow` is not directly observable in SwiftUI. Create a thin Swift wrapper:

```swift
import shared
import Combine

@MainActor
class PokemonListStore: ObservableObject {
    @Published var state: PokemonListState = PokemonListState.Loading()

    private let viewModel: PokemonListViewModel
    private var job: Kotlinx_coroutines_coreJob?

    init() {
        let repo = KoinHelper.shared.getPokemonRepository()
        viewModel = PokemonListViewModel(repository: repo)
        observe()
    }

    private func observe() {
        // Use a coroutine scope on the Swift side to collect the StateFlow
        job = FlowCollector(flow: viewModel.state) { [weak self] newState in
            self?.state = newState
        }.collect()
    }

    func loadNextPage() { viewModel.loadNextPage() }
    func search(_ query: String) { viewModel.search(query: query) }

    deinit { viewModel.onCleared() }
}
```

> Tip: Consider using [KMPNativeCoroutines](https://github.com/rickclephas/KMP-NativeCoroutines)
> to get first-class `async/await` and `AsyncStream` support for all exposed Flows.

## Screens to build

| Screen | ViewModel | Notes |
|--------|-----------|-------|
| Pokemon List | `PokemonListViewModel` | Grid, search bar, pagination |
| Pokemon Detail | `PokemonDetailViewModel(name:repo:)` | Stats, types, favourite button |
| Favourites | `FavoritesViewModel` | Observes live DB stream |

## Tech Stack (Suggested)
- SwiftUI for UI
- `NavigationStack` for routing
- `AsyncImage` or SDWebImageSwiftUI for images
