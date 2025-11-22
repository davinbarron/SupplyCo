package org.wit.supplyco.views.supplierlist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.supplyco.R
import org.wit.supplyco.adapters.SupplierAdapter
import org.wit.supplyco.adapters.SupplierListener
import org.wit.supplyco.databinding.ActivitySupplierListBinding
import org.wit.supplyco.models.SupplierModel
import org.wit.supplyco.activities.SettingsActivity
import org.wit.supplyco.views.supplier.SupplierView

class SupplierListView : AppCompatActivity(), SupplierListener {

    private lateinit var binding: ActivitySupplierListBinding
    private lateinit var presenter: SupplierListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupplierListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        presenter = SupplierListPresenter(this)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        presenter.loadSuppliers()

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean = false
            override fun onQueryTextChange(newText: String): Boolean {
                presenter.loadSuppliers(newText)
                return true
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_suppliers, menu)
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
        presenter.loadSuppliers()
    }

    override fun onSupplierClick(supplier: SupplierModel) {
        presenter.doEditSupplier(supplier)
    }

    // Methods the presenter can call back into
    fun showSuppliers(suppliers: List<SupplierModel>) {
        binding.recyclerView.adapter = SupplierAdapter(suppliers, this)
    }

    fun navigateToSupplier(supplier: SupplierModel) {
        val intent = Intent(this, SupplierView::class.java)
        intent.putExtra("supplier_edit", supplier)
        getResult.launch(intent)
    }

    fun navigateToAddSupplier() {
        val intent = Intent(this, SupplierView::class.java)
        getResult.launch(intent)
    }

    fun navigateToSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        getResult.launch(intent)
    }

    private val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                presenter.loadSuppliers()
            }
        }
}