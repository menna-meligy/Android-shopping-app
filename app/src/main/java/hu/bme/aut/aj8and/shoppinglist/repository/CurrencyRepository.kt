package hu.bme.aut.aj8and.shoppinglist.repository

import hu.bme.aut.aj8and.shoppinglist.api.CurrencyService
import hu.bme.aut.aj8and.shoppinglist.database.CurrencyDao
import hu.bme.aut.aj8and.shoppinglist.database.CurrencyEntity

class CurrencyRepository(
    private val currencyService: CurrencyService,
    private val currencyDao: CurrencyDao
) {
    suspend fun getCurrencyRate(currencyCode: String): Double {
        val cachedRate = currencyDao.getCurrency(currencyCode)?.rate
        return cachedRate ?: fetchAndCacheCurrencyRate(currencyCode)
    }

    private suspend fun fetchAndCacheCurrencyRate(currencyCode: String): Double {
        val response = currencyService.getCurrencyRates("HUF")
        val rate = response.rates[currencyCode] ?: throw Exception("Rate not found")
        currencyDao.insertAll(CurrencyEntity(currencyCode, rate))
        return rate
    }
}
