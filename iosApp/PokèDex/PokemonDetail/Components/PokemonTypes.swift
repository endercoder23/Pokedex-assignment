//
//  PokemonTypes.swift
//  PokèDex
//
//  Created by Dharamrajsinh Jadeja on 05/04/26.
//
import SwiftUI

// MARK: - Types Section
struct PokemonTypes: View {
    let types: [String]
    
    var body: some View {
        if !types.isEmpty {
            VStack(alignment: .leading, spacing: 12) {
                HStack(spacing: 8) {
                    ForEach(types, id: \.self) { type in
                        TypeBadge(type: type)
                    }
                    Spacer()
                }
                .padding(.horizontal)
                
                Divider()
                    .padding(.horizontal)
            }
        }
    }
}
