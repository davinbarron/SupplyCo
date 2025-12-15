package org.wit.supplyco.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.supplyco.databinding.CardItemBinding
import org.wit.supplyco.models.ItemModel

class ItemAdapter constructor(
    private var items: List<ItemModel>,
    private val listener: ItemListener
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemModel, listener: ItemListener) {

            binding.itemName.text = item.name
            binding.itemDescription.text = item.description
            binding.itemAmount.text = "Amount: ${item.amount}"
            binding.itemPrice.text = "Price: €${item.price}"
            binding.itemReleaseDate.text = "Release Date: ${item.releaseDate ?: "Not set"}"

            Picasso.get().load(item.image).resize(200,200).into(binding.itemImageIcon)

            binding.root.setOnClickListener {
                listener.onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = CardItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[holder.adapterPosition]
        holder.bind(item, listener)
    }

    override fun getItemCount(): Int = items.size
}

interface ItemListener {
    fun onItemClick(item: ItemModel)
}
