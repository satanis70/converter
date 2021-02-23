package com.ermilov.converter.calculator.data

import com.ermilov.converter.calculator.data.models.CurrencyResponce
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("/latest.js")
    suspend fun getRates(
        @Query("base") base: String
    ) : Response<CurrencyResponce>
}