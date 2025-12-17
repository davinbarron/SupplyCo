package org.wit.supplyco.views.suppliermap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import org.wit.supplyco.R
import org.wit.supplyco.models.SupplierModel

class SupplierMapView : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivitySupplierMapBinding
    lateinit var presenter: SupplierMapPresenter
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupplierMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        presenter = SupplierMapPresenter(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            map = it
            presenter.doPopulateMap(map)
        }
    }

    fun showSupplier(supplier: SupplierModel) {
        binding.currentTitle.text = supplier.name
        binding.currentDescription.text = supplier.description
        // optionally load image with Picasso
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doMarkerSelected(marker)
        return true
    }
}

