package com.training.droidbountyhunter

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.FragmentActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.training.models.Fugitivo
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng





/**
 * @author Giovani Gonzalez
 * Created giovani on 7/30/18
 */
class MapActivity : FragmentActivity(), OnMapReadyCallback{

    private var googleMap: GoogleMap? = null
    private var fugitivo: Fugitivo? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_mapa)

        fugitivo = intent.getParcelableExtra("fugitivo")
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        title = fugitivo!!.name
    }

    override fun onMapReady(map: GoogleMap?) {
        this.googleMap = map

        // Add a marker in Sydney and move the camera
        val position: LatLng
        if (fugitivo!!.latitude == 0.0 && fugitivo!!.longitude == 0.0) {
            position = LatLng(-34.0, 151.0)
        } else {
            position = LatLng(fugitivo!!.latitude, fugitivo!!.longitude)
        }
        googleMap!!.addMarker(MarkerOptions()
                .position(position).title(fugitivo!!.name))
        googleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 9f))
    }
}