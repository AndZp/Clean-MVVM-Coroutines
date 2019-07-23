package io.mateam.playground2.data.dataSource.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

private const val API_KEY_QUERY_PARAM_NAME = "api_key"

class TmdbAuthInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter(API_KEY_QUERY_PARAM_NAME, apiKey)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}