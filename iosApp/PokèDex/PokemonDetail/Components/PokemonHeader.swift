//
//  PokemonHeader.swift
//  PokèDex
//
//  Created by Dharamrajsinh Jadeja on 05/04/26.
//
import SwiftUI
import shared

// MARK: - Header Section
struct PokemonHeader: View {
    let pokemon: PokemonDetail
    let store: PokemonDetailStore
    
    var body: some View {
        HStack {
            VStack(alignment: .leading, spacing: 4) {
                Text(pokemon.name.capitalized)
                    .font(.title)
                    .fontWeight(.bold)
                Text("#\(String(format: "%04d", pokemon.id))")
                    .font(.subheadline)
                    .foregroundColor(.gray)
            }
            Spacer()
            Button(action: { store.toggleFavorite() }) {
                Image(systemName: pokemon.isFavorite ? "heart.fill" : "heart")
                    .font(.system(size: 20))
                    .foregroundColor(pokemon.isFavorite ? .red : .gray)
            }
        }
        .padding(.horizontal)
    }
}
