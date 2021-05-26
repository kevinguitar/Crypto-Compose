package com.kevingt.cryptocompose.ui.browser

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kevingt.cryptocompose.data.CryptoSymbol

@Composable
fun AssetBrowserView(
    model: AssetBrowserViewModel = viewModel()
) {
    val symbols = model.cryptoSymbols.collectAsState().value
    val favoriteSymbols = model.favoriteSymbols.collectAsState()

    if (symbols.isNullOrEmpty()) {
        AssetLoadingView()
    } else {
        AssetList(
            symbols = symbols,
            favoriteSymbols = favoriteSymbols,
            modifyFavorite = model::modifyFavorite,
        )
    }
}

@Composable
private fun AssetLoadingView() {
    Text(text = "Loading")
}

@Composable
private fun AssetList(
    symbols: List<CryptoSymbol>,
    favoriteSymbols: State<List<String>>,
    modifyFavorite: (String, Boolean) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(count = symbols.size) { index ->
            val symbol = symbols[index]
            AssetListItem(
                symbol = symbol,
                isFavorite = symbol.symbol in favoriteSymbols.value,
                modifyFavorite = modifyFavorite
            )
        }
    }
}

@Composable
private fun AssetListItem(
    symbol: CryptoSymbol,
    isFavorite: Boolean,
    modifyFavorite: (String, Boolean) -> Unit
) {
    Row(
        modifier = Modifier.padding(
            vertical = 4.dp,
            horizontal = 16.dp
        )
    ) {
        Text(text = symbol.baseAsset)
        Checkbox(
            checked = isFavorite,
            onCheckedChange = { modifyFavorite(symbol.symbol, it) }
        )
    }
}