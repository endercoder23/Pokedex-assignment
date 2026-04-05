//
//  ContentView.swift
//  PokèDex
//
//  Created by Dharamrajsinh Jadeja on 04/04/26.
//

import SwiftUI
import shared

struct ContentView: View {
    @State private var selectedTab = 0
    @State private var repository: PokemonRepository? = nil
    @State private var isLoading = true
    @State private var errorMessage: String? = nil
    
    var body: some View {
        Group {
            if isLoading {
                VStack(spacing: 16) {
                    ProgressView()
                        .scaleEffect(1.5)
                    Text("Initializing app...")
                        .font(.subheadline)
                        .foregroundStyle(.secondary)
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
            } else if let repo = repository {
                TabView(selection: $selectedTab) {
                    // Pokemon List Tab
                    PokemonListView(repository: repo)
                        .tabItem {
                            Label("List", systemImage: "list.bullet")
                        }
                        .tag(0)
                }
            } else {
                VStack(spacing: 16) {
                    Image(systemName: "exclamationmark.triangle.fill")
                        .font(.system(size: 48))
                        .foregroundStyle(.orange)
                    Text("Initialization Failed")
                        .font(.headline)
                    if let errorMessage = errorMessage {
                        Text(errorMessage)
                            .font(.caption)
                            .foregroundStyle(.secondary)
                            .multilineTextAlignment(.center)
                    }
                    Button(action: retryInitialization) {
                        Text("Retry")
                            .frame(maxWidth: .infinity)
                    }
                    .buttonStyle(.borderedProminent)
                }
                .padding()
            }
        }
        .task {
            // Wait for Koin to initialize and repository to be set
            try? await Task.sleep(nanoseconds: 500_000_000) // 0.5s
            retryInitialization()
        }
    }
    
    private func retryInitialization() {
        do {
            repository = try ModulesKt.getRepository()
            errorMessage = nil
            isLoading = false
        } catch {
            errorMessage = error.localizedDescription
            isLoading = false
        }
    }
}

// MARK: - Pokemon List View
private struct PokemonListView: View {
    let repository: PokemonRepository
    @StateObject private var store: PokemonListStore
    
    init(repository: PokemonRepository) {
        self.repository = repository
        _store = StateObject(wrappedValue: PokemonListStore(repository: repository))
    }
    
    var body: some View {
        NavigationStack {
            ZStack {
                switch store.state {
                case is PokemonListState.Loading:
                    ProgressView("Loading Pokémon...")
                
                case let success as PokemonListState.Success:
                    List(success.pokemon, id: \.id) { pokemon in
                        NavigationLink(destination: PokemonDetailView(pokemonName: pokemon.name)) {
                            HStack {
                                AsyncImage(url: URL(string: pokemon.imageUrl)) { image in
                                    image
                                        .resizable()
                                        .scaledToFit()
                                        .frame(width: 50, height: 50)
                                } placeholder: {
                                    ProgressView()
                                        .frame(width: 50, height: 50)
                                }
                                
                                VStack(alignment: .leading) {
                                    Text(pokemon.name.capitalized)
                                        .font(.headline)
                                }
                            }
                        }
                    }
                    .navigationTitle("Pokémon")
                
                case let error as PokemonListState.Error:
                    VStack(spacing: 12) {
                        Image(systemName: "exclamationmark.circle.fill")
                            .font(.system(size: 40))
                            .foregroundStyle(.red)
                        Text("Error")
                            .font(.headline)
                        Text(error.message)
                            .font(.caption)
                            .foregroundStyle(.secondary)
                        
                        Button(action: { store.refresh() }) {
                            Text("Retry")
                                .frame(maxWidth: .infinity)
                        }
                        .buttonStyle(.borderedProminent)
                    }
                    .padding()
                
                default:
                    EmptyView()
                }
            }
        }
    }
}

// MARK: - Pokemon Detail View (Placeholder)
private struct PokemonDetailView: View {
    let pokemonName: String
    @Environment(\.dismiss) var dismiss
    
    var body: some View {
        VStack {
            Text("Details for \(pokemonName.capitalized)")
                .font(.headline)
            Text("(To be implemented)")
                .font(.caption)
                .foregroundStyle(.secondary)
            Spacer()
        }
        .padding()
        .navigationTitle(pokemonName.capitalized)
        .navigationBarTitleDisplayMode(.inline)
    }
}

#Preview {
    ContentView()
}
