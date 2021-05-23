package com.kevingt.cryptocompose.network

import com.kevingt.cryptocompose.data.Crypto
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.channels.ReceiveChannel

/**
 *  Web Socket Market Streams to observe cryptos
 *  https://binance-docs.github.io/apidocs/spot/en/#individual-symbol-ticker-streams
 */
interface CryptoService {

    @Send
    fun subscribe(action: SubscribeAction)

    @Receive
    fun observeCryptos(): ReceiveChannel<Crypto>

    @Receive
    fun observeWebSocketEvent(): ReceiveChannel<WebSocket.Event>
}

data class SubscribeAction(
    val id: Int = 1,
    val method: String = "SUBSCRIBE",
    val params: List<String>
)