package com.assignment.pokemon.presentation.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * Lightweight KMP ViewModel base class.
 *
 * Platform integration:
 *  - Android: wrap in an AndroidViewModel and call [onCleared] from onCleared().
 *  - iOS: hold in an ObservableObject and call [onCleared] from deinit.
 */
abstract class BaseViewModel {

    val viewModelScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    open fun onCleared() {
        viewModelScope.cancel()
    }
}
