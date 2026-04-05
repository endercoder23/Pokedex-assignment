//
//  PokemonStats.swift
//  PokèDex
//
//  Created by Dharamrajsinh Jadeja on 05/04/26.
//
import SwiftUI
import shared

// MARK: - Stats Section
struct PokemonStats: View {
    let stats: [PokemonStat]
    
    var body: some View {
        if !stats.isEmpty {
            VStack(alignment: .leading, spacing: 12) {
                Text("Base Stats")
                    .font(.headline)
                    .padding(.horizontal)
                
                VStack(spacing: 10) {
                    ForEach(stats, id: \.name) { stat in
                        StatBarRow(stat: stat)
                    }
                }
                .padding()
                .background(Color(.systemGray6))
                .cornerRadius(10)
                .padding(.horizontal)
            }
        }
    }
}
