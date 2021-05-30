package com.kevingt.cryptocompose.ui.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.coil.rememberCoilPainter
import com.kevingt.cryptocompose.data.Crypto
import com.kevingt.cryptocompose.data.QUOTE_ASSET

@Composable
fun FavoriteAssetsView(
    model: FavoriteAssetsViewModel = viewModel()
) {
    val favorites = model.favorites.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 4.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        items(
            count = favorites.size,
            key = { favorites[it].symbol }
        ) { index ->
            FavoriteAssetItem(crypto = favorites[index])
        }
    }
}

/**
 *  API repo: https://github.com/TokenTax/cryptoicon-api
 */
private const val IMAGE_API = "https://cryptoicon-api.vercel.app/api/icon/"

@Composable
private fun FavoriteAssetItem(crypto: Crypto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberCoilPainter(request = "$IMAGE_API${crypto.baseAssetLowercase}"),
                    contentDescription = crypto.symbol,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
                            append(crypto.baseAsset)
                        }
                        withStyle(SpanStyle(fontSize = 10.sp)) {
                            append("/$QUOTE_ASSET")
                        }
                    },
                )
            }
            Text(text = crypto.price)
            Text(text = "${crypto.priceChangePercent}%")
        }
    }
}

@Preview
@Composable
private fun FavoriteAssetItemPreview() {
    FavoriteAssetItem(
        Crypto(
            symbol = "BTCBUSD",
            priceChangePercent = "-4.5",
            lastPrice = "34598.340000"
        )
    )
}