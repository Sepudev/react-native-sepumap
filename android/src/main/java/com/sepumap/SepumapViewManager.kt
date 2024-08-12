package com.sepumap

import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Marker
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.bridge.ReadableArray

class SepumapViewManager : SimpleViewManager<MapView>(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null
    private var markers: List<MarkerData> = emptyList()
    private var reactContext: ReactContext? = null
    private var selectedMarker: Marker? = null

    override fun getName(): String {
        return "SepumapView"
    }

    override fun createViewInstance(reactContext: ThemedReactContext): MapView {
        this.reactContext = reactContext
        val mapView = MapView(reactContext)
        mapView.onCreate(null)
        mapView.onResume()
        mapView.getMapAsync(this)
        return mapView
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        googleMap.setOnMarkerClickListener { marker ->
            selectedMarker?.hideInfoWindow()
            selectedMarker = marker
            marker.showInfoWindow() 
            sendMarkerClickEvent(marker)
            true
        }

        if (markers.isNotEmpty()) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers[0].latLng, 14f))
        }

        for (markerData in markers) {
            googleMap.addMarker(
                MarkerOptions()
                    .position(markerData.latLng)
                    .title(markerData.title)
                    .snippet("📍") 
            )
        }
    }

    private fun sendMarkerClickEvent(marker: Marker) {
        val map = Arguments.createMap()
        map.putDouble("latitude", marker.position.latitude)
        map.putDouble("longitude", marker.position.longitude)
        map.putString("title", marker.title)
        
        reactContext?.let {
            if (it.hasActiveCatalystInstance()) {
                it
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                    .emit("onMarkerClick", map)
            }
        }
    }

    @ReactProp(name = "markers")
    fun setMarkers(mapView: MapView, markers: ReadableArray) {
        val markerDataList = mutableListOf<MarkerData>()

        for (i in 0 until markers.size()) {
            val marker = markers.getMap(i)
            val lat = marker?.getDouble("latitude") ?: 0.0
            val lng = marker?.getDouble("longitude") ?: 0.0
            val title = marker?.getString("title") ?: ""
            markerDataList.add(MarkerData(LatLng(lat, lng), title))
        }

        this.markers = markerDataList

        googleMap?.let { map ->
            map.clear()
            selectedMarker = null
            for (markerData in markerDataList) {
                map.addMarker(
                    MarkerOptions()
                        .position(markerData.latLng)
                        .title(markerData.title)
                        .snippet("📍")
                )
            }

            if (markerDataList.isNotEmpty()) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(markerDataList[0].latLng, 14f))
            }
        }
    }

    data class MarkerData(val latLng: LatLng, val title: String)
}
