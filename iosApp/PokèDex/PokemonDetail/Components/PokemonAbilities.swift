//
//  PokemonAbilities.swift
//  PokèDex
//
//  Created by Dharamrajsinh Jadeja on 05/04/26.
//
import SwiftUI

// MARK: - Abilities Section
struct PokemonAbilities: View {
    let abilities: [String]
    
    var body: some View {
        if !abilities.isEmpty {
            VStack(alignment: .leading, spacing: 8) {
                Text("Abilities")
                    .font(.headline)
                    .padding(.horizontal)
                VStack(spacing: 8) {
                    ForEach(abilities, id: \.self) { ability in
                        AbilityBadge(ability: ability)
                    }
                }
                .padding(.horizontal)
            }
        }
    }
}
