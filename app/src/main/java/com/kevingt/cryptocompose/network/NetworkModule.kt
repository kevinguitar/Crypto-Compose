package com.kevingt.cryptocompose.network

import android.app.Application
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.retry.ExponentialWithJitterBackoffStrategy
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.tinder.streamadapter.coroutines.CoroutinesStreamAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

private const val BINANCE_BASE_URL = "https://api.binance.com/api/v3/"
private const val BINANCE_WEB_SOCKET = "wss://stream.binance.com:9443/ws/ticker"

private const val SOCKET_TIMEOUT = 10L
private const val BACKOFF_DURATION = 5000L

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BINANCE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideExchangeService(retrofit: Retrofit): ExchangeService {
        return retrofit.create()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideScarlet(
        application: Application,
        okHttpClient: OkHttpClient
    ): Scarlet {
        val lifecycle = AndroidLifecycle.ofApplicationForeground(application)
        val backoffStrategy = ExponentialWithJitterBackoffStrategy(
            baseDurationMillis = BACKOFF_DURATION,
            maxDurationMillis = BACKOFF_DURATION
        )

        return Scarlet.Builder()
            .webSocketFactory(okHttpClient.newWebSocketFactory(BINANCE_WEB_SOCKET))
            .addMessageAdapterFactory(GsonMessageAdapter.Factory())
            .addStreamAdapterFactory(CoroutinesStreamAdapterFactory())
            .backoffStrategy(backoffStrategy)
            .lifecycle(lifecycle)
            .build()
    }

    @Provides
    fun provideCryptoService(scarlet: Scarlet): CryptoService {
        return scarlet.create()
    }
}