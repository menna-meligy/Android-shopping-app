package com.yourpackage.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.aj8and.shoppinglist.Item
import hu.bme.aut.aj8and.shoppinglist.R
import hu.bme.aut.aj8and.shoppinglist.ShoppingDetailsActivity

class ItemAdapter(private val context: Context,
                  private val items: MutableList<Item>,
                  private val onItemClicked: (Item) -> Unit,
                  private val onDeleteClicked: (Int) -> Unit,
                  private val onViewDetailsClicked: (TextView, Item) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, onItemClicked, onViewDetailsClicked, onDeleteClicked)
    }

    override fun getItemCount(): Int = items.size

    fun addItem(item: Item) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun deleteAllItems() {
        items.clear()
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        val viewDetailsButton: Button = itemView.findViewById(R.id.viewDetailsButton)
        val iconImageView: ImageView = itemView.findViewById(R.id.iconImageView)
        val boughtCheckBox: CheckBox = itemView.findViewById(R.id.boughtCheckBox)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)

        fun bind(item: Item, onItemClicked: (Item) -> Unit, onViewDetailsClicked: (TextView, Item) -> Unit, onDeleteClicked: (Int) -> Unit) {
            nameTextView.text = item.name
            descriptionTextView.text = item.description
            priceTextView.text = "${item.estimatedPriceHUF} HUF"
            boughtCheckBox.isChecked = item.isBought
            descriptionTextView.visibility = View.GONE

            iconImageView.setImageResource(
                when (item.category) {
                    Item.Category.FOOD -> R.drawable.is_food
                    Item.Category.ELECTRONIC -> R.drawable.is_electronic
                    Item.Category.BOOK -> R.drawable.is_book
                }
            )
            itemView.setOnClickListener {
                val intent = Intent(context, ShoppingDetailsActivity::class.java).apply {
                    putExtra("ITEM_NAME", item.name)
                    putExtra("ITEM_DESCRIPTION", item.description)
                    putExtra("ITEM_PRICE_HUF", item.estimatedPriceHUF)
                    // Add other item details as needed
                }
                context.startActivity(intent)
            }

            deleteButton.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    items.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, items.size)
                }
            }

            viewDetailsButton.setOnClickListener {
                onViewDetailsClicked(descriptionTextView, item)
            }

            boughtCheckBox.setOnCheckedChangeListener { _, isChecked ->
                item.isBought = isChecked
            }
        }
    }
}
