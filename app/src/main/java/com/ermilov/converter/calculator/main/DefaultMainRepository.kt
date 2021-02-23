package com.ermilov.converter.calculator.main

import android.util.Log
import android.widget.Toast
import com.ermilov.converter.calculator.data.CurrencyApi
import com.ermilov.converter.calculator.data.models.CurrencyResponce
import com.ermilov.converter.util.Resource
import java.lang.Exception
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: CurrencyApi
) : MainRepository {
    override suspend fun getRates(base: String): Resource<CurrencyResponce> {
        return try {
            val responce = api.getRates(base)
            val result = responce.body()
            Log.i("tag", result.toString())
            if (responce.isSuccessful && result != null){
                Resource.Success(result)

            } else {
                Resource.Error(responce.message())
            }
        } catch (e: Exception){
            Resource.Error(e.message ?: "Произошла ошибка")
        }
    }
}