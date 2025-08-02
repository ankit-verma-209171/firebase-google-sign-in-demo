package com.codeitsolo.firebasedemoapp.network

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkResponseCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        // Suspend functions wrap the response type in `Call`
        if (Call::class.java != getRawType(returnType)) {
            return null
        }

        // Check if the return type is Call<NetworkResponse<T>>
        check(returnType is ParameterizedType) {
            "Return type must be a parameterized type (e.g., Call<NetworkResponse<Foo>>)"
        }

        val responseType = getParameterUpperBound(0, returnType)

        // If the response type is not NetworkResponse then we can't handle it
        if (getRawType(responseType) != NetworkResponse::class.java) {
            return null
        }

        // The response type is NetworkResponse. Now unwrap it to get the actual body type
        check(responseType is ParameterizedType) {
            "Response must be a parameterized type (e.g., NetworkResponse<Foo>)"
        }

        val successBodyType = getParameterUpperBound(0, responseType)

        return NetworkResponseCallAdapter<Any>(successBodyType)
    }
}

private class NetworkResponseCallAdapter<S : Any>(
    private val successBodyType: Type
) : CallAdapter<S, Call<NetworkResponse<S>>> {

    override fun responseType(): Type = successBodyType

    override fun adapt(call: Call<S>): Call<NetworkResponse<S>> {
        return NetworkResponseCall(call)
    }
}
