package com.kevingt.cryptocompose.ui.favorite

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.kevingt.cryptocompose.data.CryptoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteAssetsViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private val cryptoRepo: CryptoRepo
) : ViewModel() {

    val favoriteSymbols get() = cryptoRepo.favoriteSymbols

    val cryptoFlow get() = cryptoRepo.cryptoFlow
}