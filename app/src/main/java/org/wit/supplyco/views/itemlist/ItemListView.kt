package org.wit.supplyco.views.itemlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.supplyco.R
import org.wit.supplyco.adapters.ItemAdapter
import org.wit.supplyco.adapters.ItemListener
import org.wit.supplyco.databinding.ActivityItemListBinding
import org.wit.supplyco.models.ItemModel
import org.wit.supplyco.models.SupplierModel

class ItemListView : AppCompatActivity(), ItemListener {

    private lateinit var binding: ActivityItemListBinding
    lateinit var presenter: ItemListPresenter
    private lateinit var supplier: SupplierModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarItemList)

        supplier = intent.getParcelableExtra("supplier")!!

        presenter = ItemListPresenter(this, supplier)

        binding.recyclerViewItems.layoutManager = LinearLayoutManager(this)
        presenter.startListening()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> presenter.doAddItem()
            R.id.item_edit_supplier -> presenter.doEditSupplier()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(item: ItemModel) {
        presenter.doEditItem(item)
    }

    fun showItems(items: List<ItemModel>) {
        binding.recyclerViewItems.adapter = ItemAdapter(items, this)
    }
}
