package com.sepumap

import com.facebook.react.bridge.ReadableArray
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class SepumapViewManager : SimpleViewManager<MapView>(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null
    private var markers: List<LatLng> = emptyList()

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
        this.googleMap = googleMap

        if (markers.isNotEmpty()) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers[0], 12f))
        }

        for (marker in markers) {
            googleMap.addMarker(MarkerOptions().position(marker))
        }
    }

    @ReactProp(name = "markers")
    fun setMarkers(mapView: MapView, markers: ReadableArray) {
        val latLngList = mutableListOf<LatLng>()

        for (i in 0 until markers.size()) {
            val marker = markers.getMap(i)
            val lat = marker?.getDouble("latitude") ?: 0.0
            val lng = marker?.getDouble("longitude") ?: 0.0
            latLngList.add(LatLng(lat, lng))
        }

        this.markers = latLngList

        googleMap?.let { map ->
            map.clear() 
            for (marker in latLngList) {
                map.addMarker(MarkerOptions().position(marker))
            }

            if (latLngList.isNotEmpty()) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngList[0], 10f))
            }
        }
    }
}
