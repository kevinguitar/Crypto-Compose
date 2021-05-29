package com.kevingt.cryptocompose.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kevingt.cryptocompose.network.CryptoService
import com.kevingt.cryptocompose.network.SubscribeAction
import com.kevingt.cryptocompose.network.SubscribeMethod
import com.tinder.scarlet.Message
import com.tinder.scarlet.WebSocket
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*
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
    private val typeToken = object : TypeToken<List<String>>() {}.type

    private val _favoriteSymbols = MutableStateFlow<List<String>>(emptyList())
    val favoriteSymbols: StateFlow<List<String>> get() = _favoriteSymbols

    init {
        if (KEY_FAVORITES in pref) {
            val str = pref.getString(KEY_FAVORITES, null)
            _favoriteSymbols.tryEmit(gson.fromJson(str, typeToken))
        }
    }

    suspend fun subscribeToWebSocket(onCryptoReceived: (Crypto) -> Unit) {
        cryptoService.observeWebSocketEvent()
            .receiveAsFlow()
            .flowOn(Dispatchers.IO)
            .collect { event ->
                when (event) {
                    is WebSocket.Event.OnConnectionOpened<*> -> subscribeToStream()
                    is WebSocket.Event.OnMessageReceived -> {
                        val rawString = (event.message as Message.Text).value
                        val crypto = gson.fromJson(rawString, Crypto::class.java)
                        onCryptoReceived(crypto)
                    }
                    else -> Unit
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
        Timber.d("CryptoRepo: Subscribe to stream")
        val action = SubscribeAction(
            method = SubscribeMethod.SUBSCRIBE,
            params = favoriteSymbols.value.map(String::toParam)
        )
        cryptoService.subscribe(action)
    }
}

private const val KEY_FAVORITES = "favorite_cryptos"

private val String.toParam: String
    get() = "${toLowerCase(Locale.ROOT)}@ticker"