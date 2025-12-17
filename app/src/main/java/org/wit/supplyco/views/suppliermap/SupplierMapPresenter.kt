package org.wit.supplyco.views.suppliermap

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.supplyco.main.MainApp
import org.wit.supplyco.models.SupplierModel

class SupplierMapPresenter(val view: SupplierMapView) {
    private val app: MainApp = view.application as MainApp

    fun doPopulateMap(map: GoogleMap) {
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(view)

        map.setOnMapClickListener {
            view.hideSupplierCard()
        }

        app.suppliers.listenAll { suppliers ->
            map.clear()
            suppliers.forEach { supplier ->
                val loc = LatLng(supplier.lat, supplier.lng)
                val options = MarkerOptions()
                    .title(supplier.name)
                    .position(loc)

                map.addMarker(options)?.tag = supplier
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, supplier.zoom))
            }
        }
    }

    fun doMarkerSelected(marker: Marker) {
        val supplier = marker.tag as SupplierModel
        view.showSupplier(supplier)
    }
}
