//
//  PokemonDetailStore.swift
//  PokèDex
//
//  Created by Dharamrajsinh Jadeja on 05/04/26.
//


import shared
import Combine
import KMPNativeCoroutinesAsync
import OSLog

@MainActor
final class PokemonDetailStore: ObservableObject {
    @Published private(set) var state: PokemonDetailState = PokemonDetailState.Loading()
    
    private let viewModel: PokemonDetailViewModel
    private var task: Task<Void, Never>?
    
    init(name: String, repository: PokemonRepository) {
        viewModel = PokemonDetailViewModel(pokemonName: name, repository: repository)
        fetch()
    }
    
    private func fetch() {
        task = Task {
            do {
                for try await newState in asyncSequence(for: viewModel.stateFlow) {
                    self.state = newState
                }
            } catch {
                os_log("Error collecting Pokemon detail state: \(error)")
                self.state = PokemonDetailState.Error(message: error.localizedDescription)
            }
        }
    }
    
    func toggleFavorite() {
        viewModel.toggleFavorite()
    }
    
    func retry() {
        viewModel.retry()
    }
    
    deinit {
        task?.cancel()
        viewModel.onCleared()
    }
}
