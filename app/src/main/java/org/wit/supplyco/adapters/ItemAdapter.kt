package org.wit.supplyco.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.supplyco.R
import org.wit.supplyco.databinding.CardItemBinding
import org.wit.supplyco.helper.toFormattedDate
import org.wit.supplyco.models.ItemModel

class ItemAdapter constructor(
    private var items: List<ItemModel>,
    private val listener: ItemListener
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemModel, listener: ItemListener) {
            val context = binding.root.context

            binding.itemName.text = item.name
            binding.itemDescription.text = item.description
            binding.itemAmount.text = context.getString(R.string.item_amount, item.amount)
            binding.itemPrice.text = context.getString(R.string.item_price, item.price)
            binding.itemReleaseDate.text = context.getString(
                R.string.item_release_date,
                item.releaseDate.toFormattedDate(context)
            )

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
