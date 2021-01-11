package com.amk.weatherforall.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.amk.weatherforall.R
import com.amk.weatherforall.mvp.presenter.WeatherPresenter.Companion.LATITUDE_DEFAULT
import com.amk.weatherforall.mvp.presenter.WeatherPresenter.Companion.LONGITUDE_DEFAULT
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var currentMarker: Marker
    private val markers: ArrayList<Marker> = arrayListOf()

    private lateinit var latitudeTextView: TextView
    private lateinit var confirmButton: Button

    private var latitude: Double = LATITUDE_DEFAULT
    private var longitude: Double = LONGITUDE_DEFAULT


    companion object {
        const val PERMISSION_REQUEST_CODE: Int = 10


        const val LATITUDE: String = "latitude"
        const val LONGITUDE: String = "longitude"
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            latitude = location?.latitude ?: return
            longitude = location.longitude

            val position = LatLng(latitude, longitude)
            currentMarker.position = position

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 12F))
            getAddress(position)
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String?) {
        }

        override fun onProviderDisabled(provider: String?) {
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)




    }

    private fun initView() {
        latitudeTextView = findViewById(R.id.textLat)
        confirmButton = findViewById(R.id.confirm_coord_button)
        confirmButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra(LATITUDE, latitude)
            intent.putExtra(LONGITUDE, longitude)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val defaultCity = LatLng(latitude, longitude)
        getAddress(defaultCity)
        currentMarker =
            mMap.addMarker(MarkerOptions().position(defaultCity).title("Current position"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultCity))
        mMap.setOnMapLongClickListener { latLng ->
            getAddress(latLng)
            addMarker(latLng)
            latitude = latLng.latitude
            longitude = latLng.longitude
        }


    }

    private fun getAddress(latLng: LatLng) {

        val handler = Handler()
        val geocoder = Geocoder(this)

        Thread {
            try {
                val adresse = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                handler.post {
                    latitudeTextView.text = adresse[0].getAddressLine(0)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun addMarker(latLng: LatLng) {
        val title = markers.size.toString()
        mMap.clear()
        val marker: Marker = mMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
                .icon(null)
        )
        markers.add(marker)
    }

    override fun onResume() {
        super.onResume()
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        requestPermissions()
        initView()
    }

    private fun requestPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            requestLocation()
        } else {
            requestLocationPermissions()
        }
    }

    private fun requestLocationPermissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CALL_PHONE
            )
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_REQUEST_CODE
            )
        }

    }

    private fun requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED
        )
            return
        val locationManager: LocationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()


        latitude = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.latitude
            ?: LATITUDE_DEFAULT
        longitude = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.longitude
            ?: LONGITUDE_DEFAULT

        criteria.accuracy = Criteria.ACCURACY_COARSE
        val provider: String? =
            LocationManager.NETWORK_PROVIDER//locationManager.getBestProvider(criteria, true)
//        LocationManager.GPS_PROVIDER
        if (provider != null) {
//            locationManager.requestLocationUpdates(
//                provider,
//                PERIOD_OF_REQUEST,
//                DISTANCE_OF_REQUEST,
//                locationListener)
            locationManager.requestSingleUpdate(provider, locationListener, null)

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.size == 2 &&
                (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)
            ) {
                requestLocation()
            }
        }
    }


    override fun onPause() {
        super.onPause()
        (getSystemService(LOCATION_SERVICE) as LocationManager).removeUpdates(locationListener)
    }
}