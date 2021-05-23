package com.kevingt.cryptocompose.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kevingt.cryptocompose.network.ExchangeService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeInfoRepo @Inject constructor(
    @ApplicationContext context: Context,
    private val exchangeService: ExchangeService
) {

    private val pref = context.getSharedPreferences("exchange_info", MODE_PRIVATE)
    private val editor get() = pref.edit()

    private val gson = Gson()
    private val typeToken = object : TypeToken<List<CryptoSymbol>>() {}.type

    private val _cryptoSymbols = MutableStateFlow<List<CryptoSymbol>?>(null)
    val cryptoSymbols: StateFlow<List<CryptoSymbol>?> get() = _cryptoSymbols

    init {
        if (KEY_SYMBOLS in pref) {
            val str = pref.getString(KEY_SYMBOLS, null)
            _cryptoSymbols.tryEmit(gson.fromJson(str, typeToken))
        }
    }

    suspend fun getExchangeService() {
        val symbols = exchangeService.getExchangeInfo().symbols
            .filter { it.quoteAsset == "BUSD" && it.status == SymbolStatus.TRADING }

        _cryptoSymbols.emit(symbols)
        editor.putString(KEY_SYMBOLS, gson.toJson(symbols)).apply()
    }
}

private const val KEY_SYMBOLS = "symbols"