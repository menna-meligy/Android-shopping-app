package hu.bme.aut.aj8and.shoppinglist.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM CurrencyEntity WHERE currencyCode = :code")
    fun getCurrency(code: String): CurrencyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg currencies: CurrencyEntity)
}
