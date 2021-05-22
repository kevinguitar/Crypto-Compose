package com.kevingt.cryptocompose.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kevingt.cryptocompose.ui.compose.ComposeCryptoTheme
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
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                ) {
                    Column {
                        CryptoToolbar(themeRepo)
                        CryptoContent()
                    }
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
        //TODO: implement image selector
        Switch(
            checked = themeRepo.isDarkThemeState.value,
            onCheckedChange = themeRepo::setDarkTheme
        )
    }
}

@Composable
private fun CryptoContent() {
    TabRow(
        selectedTabIndex = 0,
        modifier = Modifier.fillMaxWidth()
    ) {

    }
}