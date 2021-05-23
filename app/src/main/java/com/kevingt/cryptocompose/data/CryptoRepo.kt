package com.kevingt.cryptocompose.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kevingt.cryptocompose.network.CryptoService
import com.kevingt.cryptocompose.network.SubscribeAction
import com.kevingt.cryptocompose.network.SubscribeMethod
import com.tinder.scarlet.WebSocket
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoRepo @Inject constructor(
    @ApplicationContext context: Context,
    private val cryptoService: CryptoService
) {

    private val pref = context.getSharedPreferences("crypto_repo", MODE_PRIVATE)
    private val editor get() = pref.edit()

    private val gson = Gson()
    private val typeToken = object : TypeToken<List<CryptoSymbol>>() {}.type

    private val _favoriteSymbols = MutableStateFlow<List<String>>(emptyList())
    val favoriteSymbols: StateFlow<List<String>> get() = _favoriteSymbols

    val cryptoFlow: Flow<Crypto>
        get() = cryptoService.observeCryptos().receiveAsFlow()

    init {
        if (KEY_FAVORITES in pref) {
            val str = pref.getString(KEY_FAVORITES, null)
            _favoriteSymbols.tryEmit(gson.fromJson(str, typeToken))
        }

        GlobalScope.launch {
            cryptoService.observeWebSocketEvent()
                .receiveAsFlow()
                .collect { event ->
                    if (event is WebSocket.Event.OnConnectionOpened<*>) {
                        subscribeToStream()
                    }
                    Timber.d("CryptoRepo: WebSocket event $event")
                }
        }
    }

    fun modifyFavorite(symbol: String, isAdding: Boolean) {
        val newList = if (isAdding) {
            favoriteSymbols.value + symbol
        } else {
            favoriteSymbols.value - symbol
        }
        _favoriteSymbols.tryEmit(newList)
        editor.putString(KEY_FAVORITES, gson.toJson(newList)).apply()

        val action = SubscribeAction(
            method = if (isAdding) SubscribeMethod.SUBSCRIBE else SubscribeMethod.UNSUBSCRIBE,
            params = listOf(symbol.toParam)
        )
        cryptoService.subscribe(action)
    }

    private fun subscribeToStream() {
        val action = SubscribeAction(
            method = SubscribeMethod.SUBSCRIBE,
            params = favoriteSymbols.value.map(String::toParam)
        )
        cryptoService.subscribe(action)
    }
}

private const val KEY_FAVORITES = "favorite_cryptos"

private val String.toParam: String
    get() = "$this@ticker"