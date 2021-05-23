package com.kevingt.cryptocompose.ui.favorite

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevingt.cryptocompose.data.CryptoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavoriteAssetsViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private val cryptoRepo: CryptoRepo
) : ViewModel() {



    init {
        viewModelScope.launch {
            try {

            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }
}