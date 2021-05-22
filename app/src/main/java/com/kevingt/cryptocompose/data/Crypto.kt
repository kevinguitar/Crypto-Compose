package com.kevingt.cryptocompose.data

import com.google.gson.annotations.SerializedName

data class Crypto(
    @SerializedName("s") val symbol: String,
    @SerializedName("P") val priceChangePercent: String,
    @SerializedName("c") val lastPrice: String
)