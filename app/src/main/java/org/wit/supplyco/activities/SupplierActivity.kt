package org.wit.supplyco.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.wit.supplyco.databinding.ActivitySupplierBinding
import org.wit.supplyco.main.MainApp
import timber.log.Timber.i

class SupplierActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySupplierBinding
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySupplierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Supplier Activity started..")
    }
}