package com.stkent.mapmarkerleak

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.*
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.ref.WeakReference

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, OnMapClickListener,
    OnMarkerClickListener {

    companion object {
        private const val LOG_TAG = "MapMarkerLeak"
    }

    private lateinit var googleMap: GoogleMap
    private val markerBitmapMap = mutableMapOf<Marker, Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        googleMap.setOnMapClickListener(this)
        googleMap.setOnMarkerClickListener(this)
    }

    override fun onMapClick(latLng: LatLng) {
//        val iconBitmap = BitmapDescriptorFactory.fromResource(R.drawable.custom_marker)
        val iconBitmap = BitmapFactory.decodeResource(resources, R.drawable.custom_marker)

        googleMap
            .addMarker(
                MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromBitmap(iconBitmap))
            )
            .also { addedMarker -> markerBitmapMap[addedMarker] = iconBitmap }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        marker.remove()

        val bitmap = markerBitmapMap.remove(marker)!!

        Log.d(LOG_TAG, "Bitmap recycled: ${bitmap.isRecycled}")
        bitmap.recycle()
        Log.d(LOG_TAG, "Bitmap recycled: ${bitmap.isRecycled}")

        return true
    }

}
