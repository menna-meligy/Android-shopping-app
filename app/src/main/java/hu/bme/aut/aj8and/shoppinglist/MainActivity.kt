package hu.bme.aut.aj8and.shoppinglist
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yourpackage.adapter.ItemAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private var items: MutableList<Item> = mutableListOf(
        Item("Apple", "Fresh green apple", 100, false, Item.Category.FOOD , 1),
        Item("Laptop", "Latest model of a high-end laptop", 300000, false, Item.Category.ELECTRONIC , 2),
        Item("show your work", "pyscological book by F. Scott Fitzgerald", 2500, false, Item.Category.BOOK , 3)
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ItemAdapter(
            this,
            items,
            onItemClicked = { item ->
                val intent = Intent(this@MainActivity, ShoppingDetailsActivity::class.java)
                intent.putExtra("ITEM_ID", item.id)
                startActivity(intent)
            },
            onDeleteClicked = { position ->
                items.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, items.size)
            },
            onViewDetailsClicked = { descriptionTextView, item ->
                descriptionTextView.visibility = if (descriptionTextView.visibility == View.GONE) View.VISIBLE else View.GONE
            }
        )

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val addButton: Button = findViewById(R.id.addItemButton)
        val deleteAllButton: Button = findViewById(R.id.deleteAllButton)
        val listNameTextView: TextView = findViewById(R.id.listNameTextView)

        listNameTextView.text = "MY SHOPPING LIST"

        addButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddItemActivity::class.java)
            startActivityForResult(intent, ADD_ITEM_REQUEST_CODE)
        }

        deleteAllButton.setOnClickListener {
            adapter.deleteAllItems()
        }
    }

    companion object {
        private const val ADD_ITEM_REQUEST_CODE = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_ITEM_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                val name = it.getStringExtra("ITEM_NAME") ?: return
                val description = it.getStringExtra("ITEM_DESCRIPTION") ?: return
                val price = it.getIntExtra("ITEM_PRICE", 0)
                val category = it.getStringExtra("ITEM_CATEGORY") ?: Item.Category.FOOD.name
                val purchased = it.getBooleanExtra("ITEM_PURCHASED", false)

                val itemCategory = Item.Category.valueOf(category)
                val id = it.getIntExtra("id" , 0)
                val newItem = Item(name, description, price, purchased, itemCategory , id)

                items.add(newItem)
                adapter.notifyItemInserted(items.size - 1)
            }
        }
    }
}



