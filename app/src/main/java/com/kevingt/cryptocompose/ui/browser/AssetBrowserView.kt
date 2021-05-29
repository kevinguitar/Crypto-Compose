package com.kevingt.cryptocompose.ui.browser

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kevingt.cryptocompose.data.CryptoSymbol
import com.kevingt.cryptocompose.data.SymbolStatus

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
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
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
        modifier = Modifier.padding(all = 4.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        val bgColor = if (isFavorite) {
            MaterialTheme.colors.primary
        } else {
            MaterialTheme.colors.background
        }

        Card(
            modifier = Modifier
                .clickable { modifyFavorite(symbol.symbol, !isFavorite) },
            backgroundColor = bgColor,
            shape = RoundedCornerShape(50)
        ) {
            Text(
                text = symbol.baseAsset,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }
    }
}

@Preview
@Composable
private fun AssetListItemPreview() {
    AssetListItem(
        symbol = CryptoSymbol(
            symbol = "BTCETH",
            status = SymbolStatus.TRADING,
            baseAsset = "BTC",
            quoteAsset = "ETH"
        ),
        isFavorite = true
    ) { _, _ -> }
}