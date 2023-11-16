package hu.bme.aut.aj8and.shoppinglist.api

data class ExchangeRateResponse(
    val rates: Map<String, Double>,
    // include other fields as necessary
)
