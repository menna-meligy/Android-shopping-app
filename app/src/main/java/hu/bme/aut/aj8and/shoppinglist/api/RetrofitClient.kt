import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import hu.bme.aut.aj8and.shoppinglist.api.CurrencyService

object RetrofitClient {
    private const val BASE_URL = "https://api.frankfurter.app"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val currencyApiService: CurrencyService by lazy {
        retrofit.create(CurrencyService::class.java)
    }
}

//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//val retrofit = Retrofit.Builder()
//    .baseUrl("https://api.frankfurter.app/")
//    .addConverterFactory(GsonConverterFactory.create())
//    .build()
//
//val currencyConversionService = retrofit.create(CurrencyConversionService::class.java)
