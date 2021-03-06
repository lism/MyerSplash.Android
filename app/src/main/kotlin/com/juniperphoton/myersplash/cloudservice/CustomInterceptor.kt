package com.juniperphoton.myersplash.cloudservice

import android.net.Uri
import com.juniperphoton.myersplash.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class CustomInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        val original = request.url().toString()
        val ub = Uri.parse(original).buildUpon()

        if (original.startsWith(Request.BASE_URL)) {
            ub.appendQueryParameter("client_id", BuildConfig.UNSPLASH_APP_KEY)
        }

        builder.url(ub.build().toString())

        val resp = chain.proceed(builder.build())
        if (!resp.isSuccessful) {
            throw APIException(resp.code(), request.url().toString())
        }

        return resp
    }
}