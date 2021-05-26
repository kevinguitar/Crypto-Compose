package com.kevingt.cryptocompose.ui.browser

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevingt.cryptocompose.data.CryptoRepo
import com.kevingt.cryptocompose.data.ExchangeInfoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AssetBrowserViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private val exchangeInfoRepo: ExchangeInfoRepo,
    private val cryptoRepo: CryptoRepo
) : ViewModel() {

    val cryptoSymbols get() = exchangeInfoRepo.cryptoSymbols

    val favoriteSymbols get() = cryptoRepo.favoriteSymbols

    init {
        viewModelScope.launch {
            try {
                // TODO: re-enable request here
                exchangeInfoRepo.getExchangeService()
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

    fun modifyFavorite(symbol: String, isAdding: Boolean) {
        cryptoRepo.modifyFavorite(symbol, isAdding)
    }
}