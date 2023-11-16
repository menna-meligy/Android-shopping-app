//import hu.bme.aut.aj8and.shoppinglist.model.CurrencyResponse
//import retrofit2.http.GET
//import retrofit2.http.Query
//
//interface CurrencyApiService {
//    @GET("/latest")
//    suspend fun getLatestRates(
//        @Query("from") fromCurrency: String,
//        @Query("to") toCurrencies: String
//    ): CurrencyResponse
//}

// CurrencyService.kt
package hu.bme.aut.aj8and.shoppinglist.api

import hu.bme.aut.aj8and.shoppinglist.api.CurrencyService

import hu.bme.aut.aj8and.shoppinglist.model.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {
    @GET("/latest")
    suspend fun getCurrencyRates(@Query("base") baseCurrency: String): CurrencyResponse
}
