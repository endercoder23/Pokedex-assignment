//
//  PokemonDetailView.swift
//  PokèDex
//
//  Created by Dharamrajsinh Jadeja on 05/04/26.
//

import SwiftUI
import shared

struct PokemonDetailView: View {
    let pokemonName: String
    let repo: PokemonRepository
    @StateObject private var store: PokemonDetailStore
    @Environment(\.dismiss) var dismiss
    
    init(pokemonName: String, repo: PokemonRepository) {
        self.pokemonName = pokemonName
        self.repo = repo
        _store = StateObject(wrappedValue: PokemonDetailStore(name: pokemonName, repository: repo))
    }
    
    var body: some View {
        ScrollView {
            VStack(spacing: 20) {
                switch store.state {
                case is PokemonDetailState.Loading:
                    ProgressView()
                        .padding(40)
                
                case let success as PokemonDetailState.Success:
                    let detail = success.pokemon
                    PokemonImage(imageUrl: detail.imageUrl)
                    PokemonHeader(pokemon: detail, store: store)
                    PokemonTypes(types: detail.types)
                    PokemonPhysicalAttributes(height: detail.height, weight: detail.weight)
                    PokemonStats(stats: detail.stats)
                    PokemonAbilities(abilities: detail.abilities)
                
                case let error as PokemonDetailState.Error:
                    ErrorView(message: error.message)
                
                default:
                    EmptyView()
                }
            }
            .padding(.vertical)
        }
        .navigationTitle(pokemonName.capitalized)
        .navigationBarTitleDisplayMode(.inline)
    }
}

