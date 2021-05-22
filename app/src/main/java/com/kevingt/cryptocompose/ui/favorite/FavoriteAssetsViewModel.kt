package com.kevingt.cryptocompose.ui.favorite

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteAssetsViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle
) : ViewModel() {


}