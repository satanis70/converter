package com.ermilov.converter.calculator.data.models

data class CurrencyResponce(
    val base: String,
    val date: String,
    val disclaimer: String,
    val rates: Rates,
    val timestamp: Int
)