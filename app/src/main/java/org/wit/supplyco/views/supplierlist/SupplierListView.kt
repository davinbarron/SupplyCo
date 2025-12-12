package org.wit.supplyco.views.supplierlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.supplyco.R
import org.wit.supplyco.adapters.SupplierAdapter
import org.wit.supplyco.adapters.SupplierListener
import org.wit.supplyco.databinding.ActivitySupplierListBinding
import org.wit.supplyco.models.SupplierModel

class SupplierListView : AppCompatActivity(), SupplierListener {

    private lateinit var binding: ActivitySupplierListBinding
    lateinit var presenter: SupplierListPresenter
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupplierListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        presenter = SupplierListPresenter(this)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        presenter.startListening()

        //https://developer.android.com/reference/kotlin/android/widget/SearchView.OnQueryTextListener
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean = false
            override fun onQueryTextChange(newText: String): Boolean {
                presenter.loadSuppliers(newText)
                return true
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add_supplier -> presenter.doAddSupplier()
            R.id.item_settings -> presenter.doOpenSettings()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        presenter.startListening()
    }

    override fun onSupplierClick(supplier: SupplierModel) {
        presenter.doEditSupplier(supplier, position)
    }

    fun showSuppliers(suppliers: List<SupplierModel>) {
        binding.recyclerView.adapter = SupplierAdapter(suppliers, this)
    }

    fun onDelete(position: Int) {
        binding.recyclerView.adapter?.notifyItemRemoved(position)
    }
}