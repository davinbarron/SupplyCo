package org.wit.supplyco.views.suppliermap

import android.os.Bundle
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso
import org.wit.supplyco.R
import org.wit.supplyco.databinding.ActivitySupplierMapBinding
import org.wit.supplyco.databinding.ContentSupplierMapBinding
import org.wit.supplyco.main.MainApp
import org.wit.supplyco.models.SupplierModel
import androidx.core.view.isVisible

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

        if (savedInstanceState == null) {
            animate()
        } else {
            showUiElements()
        }
    }

    private fun showUiElements() {
        binding.appBarLayout.visibility = View.VISIBLE
        contentBinding.mapView.visibility = View.VISIBLE
    }

    private fun animate() {
        binding.root.post {
            val transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.slide_down)

            TransitionManager.beginDelayedTransition(binding.root as ViewGroup, transition)

            showUiElements()
        }
    }

    fun showSupplier(supplier: SupplierModel) {
        val transition = TransitionInflater.from(this)
            .inflateTransition(R.transition.card_slide)

        TransitionManager.beginDelayedTransition(contentBinding.root as ViewGroup, transition)

        contentBinding.supplierCardMap.apply {
            supplierName.text = supplier.name
            supplierDescription.text = supplier.description
            supplierContact.text = supplier.contact
            supplierEmail.text = supplier.email
            supplierAddress.text = supplier.address
            Picasso.get()
                .load(supplier.image)
                .resize(200, 200)
                .centerCrop()
                .into(imageIcon)

            root.visibility = View.VISIBLE
        }
    }

    fun hideSupplierCard() {
        if (contentBinding.supplierCardMap.root.isVisible) {
            val transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.card_slide)

            TransitionManager.beginDelayedTransition(contentBinding.root as ViewGroup, transition)
            contentBinding.supplierCardMap.root.visibility = View.GONE
        }
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
