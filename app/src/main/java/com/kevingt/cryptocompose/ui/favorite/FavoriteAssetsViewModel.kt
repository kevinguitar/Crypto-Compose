package com.kevingt.cryptocompose.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevingt.cryptocompose.data.Crypto
import com.kevingt.cryptocompose.data.CryptoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavoriteAssetsViewModel @Inject constructor(
    private val cryptoRepo: CryptoRepo
) : ViewModel() {

    private val _favorite = MutableStateFlow<List<Crypto>>(emptyList())
    val favorites: StateFlow<List<Crypto>> get() = _favorite

    init {
        // These actions don't work in the same launch for some reason
        viewModelScope.launch {
            cryptoRepo.favoriteSymbols.collect(::updateFavorites)
        }

        viewModelScope.launch {
            cryptoRepo.subscribeToWebSocket(::onCryptoReceived)
        }
    }

    private fun updateFavorites(symbols: List<String>) {
        val oldFavorites = favorites.value
        val newFavorites = symbols
            .map { Crypto(it) }
            .toMutableList()

        oldFavorites.forEach { oldFavorite ->
            val index = newFavorites.indexOfFirst { it.symbol == oldFavorite.symbol }
            if (index == -1) return@forEach

            newFavorites[index] = oldFavorite
        }
        _favorite.value = newFavorites
    }

    private fun onCryptoReceived(crypto: Crypto) {
        val currentCryptos = favorites.value
        val position = currentCryptos.indexOfFirst { it.symbol == crypto.symbol }

        if (position == -1) {
            Timber.e("Received crypt $crypto, but not in favorite list for some reason")
            return
        }

        val newList = favorites.value.toMutableList()
        newList[position] = crypto

        _favorite.value = newList
    }
}