package com.kevingt.cryptocompose.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kevingt.cryptocompose.R
import com.kevingt.cryptocompose.ui.browser.AssetBrowserView
import com.kevingt.cryptocompose.ui.compose.ComposeCryptoTheme
import com.kevingt.cryptocompose.ui.favorite.FavoriteAssetsView
import com.kevingt.cryptocompose.utils.ThemeRepo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CryptoComposeActivity : AppCompatActivity() {

    @Inject
    lateinit var themeRepo: ThemeRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isDarkTheme = isSystemInDarkTheme()
            themeRepo.initDarkTheme(isDarkTheme)

            ComposeCryptoTheme(themeRepo.isDarkThemeState) {
                Column {
                    CryptoToolbar(themeRepo)
                    CryptoContent()
                }
            }
        }
    }
}

@Composable
private fun CryptoToolbar(themeRepo: ThemeRepo) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(64.dp)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Crypto Compose",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )
        Image(
            modifier = Modifier
                .size(24.dp)
                .clickable { themeRepo.toggleTheme() },
            painter = painterResource(
                id = if (themeRepo.isDarkThemeState.value) {
                    R.drawable.ic_moon
                } else {
                    R.drawable.ic_sun
                }
            ),
            contentDescription = "Dark Mode Switch"
        )
    }
}

@Composable
private fun CryptoContent() {
    Column {
        AssetBrowserView()
        FavoriteAssetsView()
    }
}