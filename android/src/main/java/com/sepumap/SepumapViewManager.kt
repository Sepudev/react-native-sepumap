package com.sepumap

import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng

class SepumapViewManager : SimpleViewManager<MapView>(), OnMapReadyCallback {

    override fun getName(): String {
        return "SepumapView"
    }

    override fun createViewInstance(reactContext: ThemedReactContext): MapView {
        val mapView = MapView(reactContext)
        mapView.onCreate(null)
        mapView.onResume()
        mapView.getMapAsync(this)
        return mapView
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val location = LatLng(-33.852, 151.211)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
    }
}
