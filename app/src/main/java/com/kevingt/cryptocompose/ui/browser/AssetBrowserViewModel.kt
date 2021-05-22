package com.kevingt.cryptocompose.ui.browser

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AssetBrowserViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle
) : ViewModel() {

    init {

    }
}