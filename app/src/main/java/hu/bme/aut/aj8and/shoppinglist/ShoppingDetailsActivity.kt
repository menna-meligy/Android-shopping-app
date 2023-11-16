package hu.bme.aut.aj8and.shoppinglist

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import hu.bme.aut.aj8and.shoppinglist.database.AppDatabase
import hu.bme.aut.aj8and.shoppinglist.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShoppingDetailsActivity : AppCompatActivity() {
    private lateinit var currencyRepository: CurrencyRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_details)

        val currencyService = RetrofitClient.currencyApiService
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "AppDatabase"
        ).build()
        val currencyDao = database.currencyDao()
        currencyRepository = CurrencyRepository(currencyService, currencyDao)

        val itemName = intent.getStringExtra("ITEM_NAME") ?: "Unknown"
        val itemDescription = intent.getStringExtra("ITEM_DESCRIPTION") ?: "No description"
        val itemPriceHUF = intent.getIntExtra("ITEM_PRICE_HUF", -1)

        if (itemPriceHUF == -1) {
            Log.e("ShoppingDetailsActivity", "Invalid price passed in intent extras")
            finish()
        }

        val nameTextView = findViewById<TextView>(R.id.itemNameTextView)
        val descriptionTextView = findViewById<TextView>(R.id.itemDescriptionTextView)
        val priceTextView = findViewById<TextView>(R.id.itemPriceTextView)

        nameTextView.text = itemName
        descriptionTextView.text = itemDescription
        priceTextView.text = String.format(getString(R.string.price_with_currency), itemPriceHUF.toFloat(), "HUF")


        fetchAndDisplayConversionRates(itemPriceHUF)
    }

    private fun fetchAndDisplayConversionRates(priceInHUF: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val targetCurrencies = listOf("USD", "EUR", "GBP")
                val conversionRates = targetCurrencies.associateWith { currencyCode ->
                    currencyRepository.getCurrencyRate(currencyCode)
                }
                withContext(Dispatchers.Main) {
                    updateConversionViews(conversionRates, priceInHUF)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("ShoppingDetailsActivity", "Error fetching conversion rates", e)
                    Toast.makeText(this@ShoppingDetailsActivity, "Failed to load conversion rates.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun updateConversionViews(conversionRates: Map<String, Double>, priceInHUF: Int) {
        val priceInUSDTextView = findViewById<TextView>(R.id.priceInUSDTextView)
        val priceInEURTextView = findViewById<TextView>(R.id.priceInEURTextView)
        val priceInGBPTextView = findViewById<TextView>(R.id.priceInGBPTextView)

        val usdRate = conversionRates["USD"] ?: 0.0
        val eurRate = conversionRates["EUR"] ?: 0.0
        val gbpRate = conversionRates["GBP"] ?: 0.0

        priceInUSDTextView.text = String.format(getString(R.string.price_with_currency), priceInHUF * usdRate, "USD")
        priceInEURTextView.text = String.format(getString(R.string.price_with_currency), priceInHUF * eurRate, "EUR")
        priceInGBPTextView.text = String.format(getString(R.string.price_with_currency), priceInHUF * gbpRate, "GBP")
    }

}
