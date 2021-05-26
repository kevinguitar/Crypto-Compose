package com.kevingt.cryptocompose.ui.favorite

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FavoriteAssetsView(
    model: FavoriteAssetsViewModel = viewModel()
) {
    val favoriteSymbols = model.favoriteSymbols.collectAsState().value

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(count = favoriteSymbols.size) { index ->
            Text(text = favoriteSymbols[index])
        }
    }
}