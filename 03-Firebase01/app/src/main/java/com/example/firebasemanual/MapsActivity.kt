package com.example.firebasemanual

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var tienePermisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        solicitarPermisos()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null){
            mMap = googleMap
            mMap.setOnPolygonClickListener {
                Log.i("mapa", "setOnPolygonClickListener ${it}")
            }
            mMap.setOnPolylineClickListener {
                Log.i("mapa", "setOnPolylineClickListener ${it}")
            }
            mMap.setOnCameraMoveListener {
                Log.i("mapa", "setOnCameraMoveListener")
            }
            mMap.setOnMarkerClickListener {
                Log.i("mapa", "setOnMarkerClickListener ${it}")
                return@setOnMarkerClickListener true
            }
            mMap.setOnCameraMoveStartedListener {
                Log.i("mapa", "setOnCameraMoveStartedListener ${it}")
            }
            mMap.setOnCameraIdleListener {
                Log.i("mapa", "setOnCameraIdleListener")
            }
            establecerConfiguracionMapa(mMap)
            val quicentro = LatLng(-0.176125, -78.480208)
            val titulo = "Quicentro"
            val zoom = 17f
            añadirMarcador(quicentro, titulo)
            moverCamaraZoom(quicentro, zoom)

            val poliLineaUno = googleMap
                .addPolyline(
                    PolylineOptions()
                        .clickable(true)
                        .add(
                            LatLng (-0.176125, -78.480208),
                            LatLng (-0.176125, -78.480208),
                            LatLng (-0.176125, -78.480208)
                        )
                )
            poliLineaUno.tag = "Linea-1"

            val poligonoUnos = googleMap
                .addPolygon(
                    PolygonOptions()
                        .clickable(true)
                        .add(
                            LatLng (-0.176125, -78.480208),
                            LatLng (-0.176125, -78.480208),
                            LatLng (-0.176125, -78.480208)
                        )
                )
            poligonoUnos.fillColor = -0xc771c4
            poligonoUnos.tag = "poligono-2"
        }
    }

    fun añadirMarcador(latLng: LatLng, title: String){
        mMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
        )
    }

    fun moverCamaraZoom(latLng: LatLng, zoom: Float = 10f){
        mMap.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(latLng, zoom)
        )
    }

    fun solicitarPermisos(){
        val permisosFineLocation = ContextCompat
            .checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if (tienePermisos){
            Log.i("mapa", "Tiene permisos FINE LOCATION")
            this.tienePermisos = true
        }else{
            ActivityCompat.requestPermissions(
                this, //contexto
                arrayOf( //Arreglo Permisos
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1 // Codigo que esperamos
            )
        }
    }

    fun establecerConfiguracionMapa (mapa:GoogleMap){
        val contexto =  this.applicationContext
        with(mapa){
            val permisosFinelocation = ContextCompat
                .checkSelfPermission(
                    contexto,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val tienePermisos = permisosFinelocation == PackageManager.PERMISSION_GRANTED
            if (tienePermisos){
                mapa.isMyLocationEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
        }
    }
}