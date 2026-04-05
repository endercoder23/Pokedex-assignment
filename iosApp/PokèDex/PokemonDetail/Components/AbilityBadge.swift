//
//  AbilityBadge.swift
//  PokèDex
//
//  Created by Dharamrajsinh Jadeja on 05/04/26.
//
import SwiftUI

// MARK: - Ability Badge
struct AbilityBadge: View {
    let ability: String
    
    var body: some View {
        HStack {
            Image(systemName: "star.fill")
                .font(.caption2)
                .foregroundColor(.blue)
            Text(ability.capitalized)
                .font(.caption)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(8)
        .background(Color.blue.opacity(0.08))
        .cornerRadius(6)
    }
}
