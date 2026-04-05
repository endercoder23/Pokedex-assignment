//
//  PokemonImage.swift
//  PokèDex
//
//  Created by Dharamrajsinh Jadeja on 05/04/26.
//
import SwiftUI

struct PokemonImage: View {
    let imageUrl: String
    
    var body: some View {
        AsyncImage(url: URL(string: imageUrl)) { image in
            image.resizable().scaledToFit()
        } placeholder: {
            Color.gray.opacity(0.1)
        }
        .frame(height: 240)
        .frame(maxWidth: .infinity)
        .background(Color(.systemGray6))
        .cornerRadius(12)
        .padding()
    }
}
