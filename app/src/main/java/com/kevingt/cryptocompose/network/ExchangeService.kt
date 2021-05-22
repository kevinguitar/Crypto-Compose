package com.kevingt.cryptocompose.network

import com.kevingt.cryptocompose.data.ExchangeInfo
import retrofit2.http.GET

interface ExchangeService {

    /**
     *  Exchange Information
     *  Current exchange trading rules and symbol information
     *
     *  doc: https://binance-docs.github.io/apidocs/spot/en/#exchange-information
     */
    @GET("exchangeInfo")
    suspend fun getExchangeInfo(): ExchangeInfo
}