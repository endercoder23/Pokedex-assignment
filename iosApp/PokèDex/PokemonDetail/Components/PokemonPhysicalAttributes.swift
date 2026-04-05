//
//  PokemonPhysicalAttributes.swift
//  PokèDex
//
//  Created by Dharamrajsinh Jadeja on 05/04/26.
//
import SwiftUI

// MARK: - Physical Attributes Section
struct PokemonPhysicalAttributes: View {
    let height: Int32
    let weight: Int32
    
    var body: some View {
        HStack(spacing: 0) {
            VStack(alignment: .center, spacing: 6) {
                Text(String(format: "%.1f m", Double(height) / 10))
                    .font(.headline)
                    .fontWeight(.semibold)
                Text("Height")
                    .font(.caption2)
                    .foregroundColor(.gray)
            }
            .frame(maxWidth: .infinity)
            
            Divider()
                .frame(height: 40)
            
            VStack(alignment: .center, spacing: 6) {
                Text(String(format: "%.1f kg", Double(weight) / 10))
                    .font(.headline)
                    .fontWeight(.semibold)
                Text("Weight")
                    .font(.caption2)
                    .foregroundColor(.gray)
            }
            .frame(maxWidth: .infinity)
        }
        .padding()
        .background(Color(.systemGray6))
        .cornerRadius(10)
        .padding(.horizontal)
    }
}
