package com.ermilov.converter.calculator.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ermilov.converter.calculator.data.models.Rates
import com.ermilov.converter.util.DispatcherProvider
import com.ermilov.converter.util.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.round

class MainViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {
    sealed class CurrencyEvent{
        class Succes(val resultText: String): CurrencyEvent()
        class Failure(val errorText: String): CurrencyEvent()
        object Empty : CurrencyEvent()
    }

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion

    fun convert(
        amountStr: String,
        fromCurrency: String,
        toCurrency: String
    ) {
        val fromAmount = amountStr.toFloatOrNull()
        if (fromAmount == null){
            _conversion.value = CurrencyEvent.Failure("Не валидная сумма")
            return
    }

        viewModelScope.launch(dispatchers.io){
            when(val ratesResponse = repository.getRates(fromCurrency)){
                is Resource.Error -> _conversion.value = CurrencyEvent.Failure(ratesResponse.message!!)
                is Resource.Success -> {
                    val rates = ratesResponse.data!!.rates
                    val rate = getRateForCurrency(toCurrency, rates)
                    if (rate == null){
                        _conversion.value = CurrencyEvent.Failure("Неизвестная ошибка")
                    } else {
                        val convertedCurrency = round(fromAmount * rate * 100) / 100
                        _conversion.value = CurrencyEvent.Succes(
                            "$fromAmount $fromCurrency = $convertedCurrency $toCurrency"
                        )
                    }
                }
            }
        }

        }

    private fun getRateForCurrency(currency: String, rates: Rates) = when (currency) {
        "AUD" -> rates.AUD
        "AZN" -> rates.AZN
        "GBP" -> rates.GBP
        "AMD" -> rates.AMD
        "BYN" -> rates.BYN
        "BGN" -> rates.BGN
        "BRL" -> rates.BRL
        "HUF" -> rates.HUF
        "HKD" -> rates.HKD
        "DKK" -> rates.DKK
        "USD" -> rates.USD
        "EUR" -> rates.EUR
        "INR" -> rates.INR
        "KZT" -> rates.KZT
        "CAD" -> rates.CAD
        "KGS" -> rates.KGS
        "CNY" -> rates.CNY
        "MDL" -> rates.MDL
        "NOK" -> rates.NOK
        "PLN" -> rates.PLN
        "RON" -> rates.RON
        "XDR" -> rates.XDR
        "SGD" -> rates.SGD
        "TJS" -> rates.TJS
        "TRY" -> rates.TRY
        "TMT" -> rates.TMT
        "UZS" -> rates.UZS
        "UAH" -> rates.UAH
        "CZK" -> rates.CZK
        "SEK" -> rates.SEK
        "CHF" -> rates.CHF
        "ZAR" -> rates.ZAR
        "KRW" -> rates.KRW
        "JPY" -> rates.JPY
        else -> null
    }

}