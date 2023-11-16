package hu.bme.aut.aj8and.shoppinglist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class AddItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        val nameEditText: EditText = findViewById(R.id.nameEditText)
        val priceEditText: EditText = findViewById(R.id.priceEditText)
        val descriptionEditText: EditText = findViewById(R.id.descriptionEditText)
        val categorySpinner: Spinner = findViewById(R.id.categorySpinner)
        val purchasedCheckBox: CheckBox = findViewById(R.id.purchasedCheckBox)
        val addItemButton: Button = findViewById(R.id.addItemButton)

        val categories = Item.Category.values().map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        addItemButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("ITEM_NAME", nameEditText.text.toString())
            resultIntent.putExtra("ITEM_DESCRIPTION", descriptionEditText.text.toString())
            resultIntent.putExtra("ITEM_PRICE", priceEditText.text.toString().toIntOrNull() ?: 0)
            resultIntent.putExtra("ITEM_CATEGORY", categorySpinner.selectedItem.toString())
            resultIntent.putExtra("ITEM_PURCHASED", purchasedCheckBox.isChecked)

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

    }
}
