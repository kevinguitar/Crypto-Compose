package com.kevingt.cryptocompose.ui.favorite

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kevingt.cryptocompose.data.Crypto

@Composable
fun FavoriteAssetsView(
    model: FavoriteAssetsViewModel = viewModel()
) {
    val favorites = model.favorites.collectAsState().value

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(count = favorites.size) { index ->
            FavoriteAssetItem(crypto = favorites[index])
        }
    }
}

@Composable
fun FavoriteAssetItem(
    crypto: Crypto
) {
    Text(text = "${crypto.symbol}, ${crypto.priceChangePercent}, ${crypto.lastPrice}")
}