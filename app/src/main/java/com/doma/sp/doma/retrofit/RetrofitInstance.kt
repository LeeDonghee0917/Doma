package com.doma.sp.doma.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: BookService
        get() = retrofit.create(BookService::class.java)
    private val retrofit: Retrofit
        private get() {
            val gson = GsonBuilder().setLenient().create()

            return Retrofit.Builder()
                .baseUrl(BookService.RANK_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
}