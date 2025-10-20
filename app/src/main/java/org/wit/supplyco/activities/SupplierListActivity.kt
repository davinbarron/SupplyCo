package org.wit.supplyco.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.supplyco.R
import org.wit.supplyco.adapters.SupplierAdapter
import org.wit.supplyco.adapters.SupplierListener
import org.wit.supplyco.databinding.ActivitySupplierListBinding
import org.wit.supplyco.main.MainApp
import org.wit.supplyco.models.SupplierModel

class SupplierListActivity : AppCompatActivity(), SupplierListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivitySupplierListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupplierListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        refreshList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_suppliers, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add_supplier -> {
                val launcherIntent = Intent(this, SupplierActivity::class.java)
                getResult.launch(launcherIntent)
            }
            R.id.item_settings -> {
                val settingsIntent = Intent(this, SettingsActivity::class.java)
                getResult.launch(settingsIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // onResume() ensures the adapter reflects the current state of the suppliers array list
    // called when we navigate back to the suppliers list
    override fun onResume() {
        super.onResume()
        refreshList()
    }

    override fun onSupplierClick(supplier: SupplierModel) {
        val launcherIntent = Intent(this, SupplierActivity::class.java)
        launcherIntent.putExtra("supplier_edit", supplier)
        getResult.launch(launcherIntent)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                refreshList()
            }
        }

    private fun refreshList() {
        binding.recyclerView.adapter = SupplierAdapter(app.suppliers.findAll(), this)
    }
}