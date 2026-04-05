//
//  TypeBadge.swift
//  PokèDex
//
//  Created by Dharamrajsinh Jadeja on 05/04/26.
//
import SwiftUI

// MARK: - Type Badge
struct TypeBadge: View {
    let type: String
    
    var typeColor: Color {
        switch type.lowercased() {
        case "fire": return Color(red: 0.94, green: 0.50, blue: 0.29)
        case "water": return Color(red: 0.20, green: 0.60, blue: 0.95)
        case "grass": return Color(red: 0.44, green: 0.77, blue: 0.35)
        case "electric": return Color(red: 0.98, green: 0.81, blue: 0.20)
        case "ice": return Color(red: 0.60, green: 0.84, blue: 0.96)
        case "fighting": return Color(red: 0.78, green: 0.20, blue: 0.20)
        case "poison": return Color(red: 0.69, green: 0.23, blue: 0.78)
        case "ground": return Color(red: 0.92, green: 0.83, blue: 0.41)
        case "flying": return Color(red: 0.69, green: 0.61, blue: 0.95)
        case "psychic": return Color(red: 0.96, green: 0.47, blue: 0.70)
        case "bug": return Color(red: 0.70, green: 0.79, blue: 0.08)
        case "rock": return Color(red: 0.74, green: 0.66, blue: 0.44)
        case "ghost": return Color(red: 0.52, green: 0.44, blue: 0.66)
        case "dragon": return Color(red: 0.50, green: 0.25, blue: 0.95)
        case "dark": return Color(red: 0.44, green: 0.33, blue: 0.26)
        case "steel": return Color(red: 0.68, green: 0.73, blue: 0.83)
        case "fairy": return Color(red: 0.95, green: 0.50, blue: 0.84)
        case "normal": return Color(red: 0.80, green: 0.80, blue: 0.75)
        default: return Color(red: 0.70, green: 0.70, blue: 0.70)
        }
    }
    
    var body: some View {
        Text(type.capitalized)
            .font(.caption2)
            .fontWeight(.semibold)
            .foregroundColor(.white)
            .padding(.horizontal, 10)
            .padding(.vertical, 5)
            .background(typeColor)
            .cornerRadius(5)
    }
}
