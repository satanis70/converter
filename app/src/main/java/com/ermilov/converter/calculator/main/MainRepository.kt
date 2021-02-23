package com.ermilov.converter.calculator.main

import com.ermilov.converter.calculator.data.models.CurrencyResponce
import com.ermilov.converter.util.Resource

interface MainRepository {
    suspend fun getRates(base: String): Resource<CurrencyResponce>
}