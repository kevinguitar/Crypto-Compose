package com.kevingt.cryptocompose.ui.browser

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AssetBrowserView(
    model: AssetBrowserViewModel = viewModel()
) {
    val symbols = model.cryptoSymbol.collectAsState()

    Column {
        Text(text = "Asset Browser!")
        Text(text = symbols.value?.map { it.baseAsset }?.joinToString(", ").orEmpty())
    }
}