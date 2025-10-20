package org.wit.supplyco.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.wit.supplyco.R
import org.wit.supplyco.databinding.ActivitySettingsBinding
import org.wit.supplyco.main.MainApp
import timber.log.Timber.i

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarSettings)

        app = application as MainApp
        i("Settings Activity started..")

        binding.buttonDeleteAllSuppliers.setOnClickListener {

            i("Delete All button pressed")

            app.suppliers.deleteAll()

            i("${app.suppliers.findAll().size} suppliers in the list")

            Toast
                .makeText(this, "All suppliers deleted", Toast.LENGTH_SHORT)
                .show()

            setResult(RESULT_OK)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_supplier, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}