package com.kevingt.cryptocompose.network

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.random.Random

/**
 *  Web Socket Market Streams to observe cryptos
 *  https://binance-docs.github.io/apidocs/spot/en/#individual-symbol-ticker-streams
 */
interface CryptoService {

    @Receive
    fun observeWebSocketEvent(): ReceiveChannel<WebSocket.Event>

    @Send
    fun subscribe(action: SubscribeAction)
}

private val randomInt: Int
    get() = Random(System.currentTimeMillis()).nextInt(100)

data class SubscribeAction(
    val id: Int = randomInt,
    val method: SubscribeMethod,
    val params: List<String>
)

enum class SubscribeMethod {
    SUBSCRIBE, UNSUBSCRIBE
}