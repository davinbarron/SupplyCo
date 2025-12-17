package org.wit.supplyco.views.suppliermap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso
import org.wit.supplyco.databinding.ActivitySupplierMapBinding
import org.wit.supplyco.databinding.ContentSupplierMapBinding
import org.wit.supplyco.main.MainApp
import org.wit.supplyco.models.SupplierModel

class SupplierMapView : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivitySupplierMapBinding
    private lateinit var contentBinding: ContentSupplierMapBinding
    lateinit var app: MainApp
    lateinit var presenter: SupplierMapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp
        binding = ActivitySupplierMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarMap)

        presenter = SupplierMapPresenter(this)

        contentBinding = binding.contentSupplierMap

        contentBinding.mapView.onCreate(savedInstanceState)
        contentBinding.mapView.getMapAsync {
            presenter.doPopulateMap(it)
        }
    }

    fun showSupplier(supplier: SupplierModel) {
        contentBinding.currentTitle.text = supplier.name
        contentBinding.currentDescription.text = supplier.description
        Picasso.get()
            .load(supplier.image)
            .into(contentBinding.currentImage)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doMarkerSelected(marker)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }
}
