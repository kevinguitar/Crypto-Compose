package com.kevingt.cryptocompose.data

import com.kevingt.cryptocompose.network.CryptoService
import com.kevingt.cryptocompose.network.SubscribeAction
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoRepo @Inject constructor(
    private val cryptoService: CryptoService
) {

    init {
        GlobalScope.launch {
            cryptoService.observeWebSocketEvent()
                .receiveAsFlow()
                .collect { event ->
                        Timber.d("Connect event $event")
                        when (event) {
                            is WebSocket.Event.OnConnectionOpened<*> -> subscribeWhenConnected()
                            is WebSocket.Event.OnConnectionFailed -> Unit
                            else -> Unit
                        }
                    }

            cryptoService.observeCryptos()
                .receiveAsFlow()
                .collect { crypto ->
                    Timber.d("Crypto ${crypto.symbol} ${crypto.lastPrice}")
                }
        }
    }

    private fun subscribeWhenConnected() {
        cryptoService.subscribe(
            SubscribeAction(
                params = listOf(
                    "btcbusd$STREAM_SUFFIX",
                    "ethbusd$STREAM_SUFFIX"
                )
            )
        )
    }
}

private const val STREAM_SUFFIX = "@ticker"