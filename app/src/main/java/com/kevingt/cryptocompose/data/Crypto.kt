package com.kevingt.cryptocompose.data

import com.google.gson.annotations.SerializedName
import java.util.*

const val QUOTE_ASSET = "BUSD"

data class Crypto(
    @SerializedName("s") val symbol: String,
    @SerializedName("P") val priceChangePercent: String = "0",
    @SerializedName("c") val lastPrice: String = "loading..."
) {

    val baseAsset: String
        get() = symbol.removeSuffix(QUOTE_ASSET)

    val baseAssetLowercase: String
        get() = baseAsset.toLowerCase(Locale.ROOT)

    val price: String
        get() = lastPrice.dropLastWhile { c -> c == '0' }
}