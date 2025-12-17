package org.wit.supplyco.views.suppliermap

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.supplyco.main.MainApp

class SupplierMapPresenter(val view: SupplierMapView) {
    private val app: MainApp = view.application as MainApp

    fun doPopulateMap(map: GoogleMap) {
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(view)

        app.suppliers.findAll { suppliers ->
            suppliers.forEach {
                val loc = LatLng(it.lat, it.lng)
                val options = MarkerOptions().title(it.name).position(loc)
                map.addMarker(options)?.tag = it.id
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
            }
        }
    }

    fun doMarkerSelected(marker: Marker) {
        val id = marker.tag as String
        app.suppliers.findSupplier(id) { list ->
            val supplier = list.firstOrNull()
            if (supplier != null) view.showSupplier(supplier)
        }
    }
}
