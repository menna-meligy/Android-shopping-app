//package hu.bme.aut.aj8and.shoppinglist.database
//
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//
//@Entity
//data class ItemEntity(
//    @PrimaryKey val id: Long,
//    val name: String,
//    val description: String,
//    val priceHUF: Int
//    // Add other fields as needed
//)
package hu.bme.aut.aj8and.shoppinglist.database
import androidx.room.Entity
import androidx.room.PrimaryKey
//
//@Entity
//data class ItemEntity(
//    @PrimaryKey val id: Long,
//    val name: String,
//    val price: Double
//)
@Entity
data class CurrencyEntity(
    @PrimaryKey val currencyCode: String,
    val rate: Double
)