package hu.bme.aut.aj8and.shoppinglist



data class Item(
    val name: String,
    val description: String,
    val estimatedPriceHUF: Int,
    var isBought: Boolean,
    val category: Category,
    val id : Int
) {
    enum class Category {
        FOOD, ELECTRONIC, BOOK
    }
    }
