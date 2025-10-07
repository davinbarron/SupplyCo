package org.wit.supplyco.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.supplyco.databinding.CardSupplierBinding
import org.wit.supplyco.models.SupplierModel

class SupplierAdapter constructor(private var suppliers: List<SupplierModel>) :
    RecyclerView.Adapter<SupplierAdapter.SupplierViewHolder>() {

    class SupplierViewHolder(private val binding : CardSupplierBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(supplier: SupplierModel) {
            binding.supplierName.text = supplier.name
            binding.supplierDescription.text = supplier.description
            binding.supplierContact.text = supplier.contact
            binding.supplierEmail.text = supplier.email
            binding.supplierAddress.text = supplier.address
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupplierViewHolder {
        val binding = CardSupplierBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return SupplierViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SupplierViewHolder, position: Int) {
        val supplier = suppliers[holder.adapterPosition]
        holder.bind(supplier)
    }

    override fun getItemCount(): Int = suppliers.size
}