package com.kevingt.cryptocompose.data

data class ExchangeInfo(
    val symbols: List<CryptoSymbol>
)

data class CryptoSymbol(
    val symbol: String,
    val status: SymbolStatus,
    val baseAsset: String,
    val quoteAsset: String
)

enum class SymbolStatus { TRADING, BREAK }