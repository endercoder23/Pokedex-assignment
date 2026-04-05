//
//  StatBarRow.swift
//  PokèDex
//
//  Created by Dharamrajsinh Jadeja on 05/04/26.
//
import SwiftUI
import shared

// MARK: - Stats Bar Row
struct StatBarRow: View {
    let stat: PokemonStat
    
    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            HStack {
                Text(stat.name.capitalized)
                    .font(.caption2)
                    .foregroundColor(.gray)
                    .frame(width: 55, alignment: .leading)
                Text("\(stat.value)")
                    .font(.caption2)
                    .fontWeight(.semibold)
            }
            GeometryReader { geo in
                ZStack(alignment: .leading) {
                    RoundedRectangle(cornerRadius: 3)
                        .fill(Color(.systemGray5))
                    RoundedRectangle(cornerRadius: 3)
                        .fill(Color.blue)
                        .frame(width: geo.size.width * CGFloat(stat.value) / 150.0)
                }
            }
            .frame(height: 5)
        }
    }
}
