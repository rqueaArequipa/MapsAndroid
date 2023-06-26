package com.miempresa.googlemapv4

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat
import android.Manifest
import android.location.Address
import android.location.Geocoder
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.miempresa.googlemapv4.databinding.ActivityMapsBinding
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, AdapterView.OnItemSelectedListener{
	private var destino = ""
	private var img = ""
	private var mensaje = ""
	var marcadorDestino: Marker? = null
	var coordenada = LatLng(0.0, 0.0)
	private val Plaza = LatLng(-16.3958598,-71.5419616)
	private lateinit var mMap : GoogleMap
	private lateinit var binding : ActivityMapsBinding
	private val REQUEST_LOCATION_PERMISSION = 1
	private lateinit var fusedLocationClient: FusedLocationProviderClient

	private val tipos_mapas = intArrayOf(
		GoogleMap.MAP_TYPE_NONE,
		GoogleMap.MAP_TYPE_NORMAL,
		GoogleMap.MAP_TYPE_SATELLITE,
		GoogleMap.MAP_TYPE_HYBRID,
		GoogleMap.MAP_TYPE_TERRAIN,
	)


	override fun onCreate(savedInstanceState : Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMapsBinding.inflate(layoutInflater)
		setContentView(binding.root)

		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		val mapFragment = supportFragmentManager
			.findFragmentById(R.id.map) as SupportMapFragment
		mapFragment.getMapAsync(this)

		spnTipoMapa.setOnItemSelectedListener(this)

		val recibidos = this.intent.extras
		if (recibidos != null) {
			destino = intent.extras!!.getString("destino")!!
			img = intent.extras!!.getString("img")!!
		}

		fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

	}
	override fun onMapReady(googleMap : GoogleMap) {
		mMap = googleMap
		mMap.getUiSettings().setAllGesturesEnabled(true)
		mMap.getUiSettings().setZoomControlsEnabled(true)
		mMap.getUiSettings().setCompassEnabled(true)
		mMap.isBuildingsEnabled = true
		mMap.setMinZoomPreference(10f)

		mMap.getUiSettings().setCompassEnabled(true)
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
			ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			mMap.setMyLocationEnabled(true);
		} else {
			ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_LOCATION_PERMISSION)
		}

		val searchView = buscar
		searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
			override fun onQueryTextSubmit(query: String): Boolean {
				searchForLocation(query)
				return true
			}

			override fun onQueryTextChange(newText: String): Boolean {
				return false
			}
		})

		when (destino) {
			"plaza de armas" -> {
				coordenada = LatLng(-16.3988031, -71.5374435)
				marcadorDestino = mMap.addMarker(MarkerOptions().position(coordenada).title("Conoce: $destino"))
				mensaje = "La plaza Mayor o plaza de Armas de Arequipa, " +
						"es uno de los principales espacios públicos de Arequipa " +
						"y el lugar de fundación de la ciudad"
			}

			"characato" -> {
				coordenada = LatLng(-16.4703683,-71.5015987)
				marcadorDestino = mMap.addMarker(MarkerOptions().position(coordenada).title("Conoce: $destino"))
				mensaje = "es uno de los 29 distritos que conforman la provincia de Arequipa en el departamento de Arequipa," +
						" bajo la administración del Gobierno regional de Arequipa, en el sur del Perú"
			}

			"colca" -> {
				coordenada = LatLng(-16.397375,-71.5360895)
				marcadorDestino = mMap.addMarker(MarkerOptions().position(coordenada).title("Conoce: $destino"))
				mensaje = "El distrito de Colca en el Perú, está ubicado en la Provincia de Víctor Fajardo," +
						" en el Departamento de Ayacucho. Población Compuesta esencialmente por comunidades campesinas" +
						" de 857 habitantes aproximadamente"
			}

			"yura" -> {
				coordenada = LatLng(-16.3073363,-71.6295746)
				marcadorDestino = mMap.addMarker(MarkerOptions().position(coordenada).title("Conoce: $destino"))
				mensaje = "El distrito de Yura es uno de los 29 distritos que conforman la provincia de Arequipa en el departamento de Arequipa," +
						" bajo la administración del Gobierno regional de Arequipa, en el sur del Perú."
			}

			"mirador sachaca" -> {
				coordenada = LatLng(-16.4267931,-71.5685479)
				marcadorDestino = mMap.addMarker(MarkerOptions().position(coordenada).title("Conoce: $destino"))
				mensaje = "El Mirador de Sachaca es un mirador de torre de 19 metros de altura, fue construido en 1988 en base a fierro y madera," +
						" en lo que era la cima del cerro que albergaba el antiguo cementerio de Sachaca, en 1996 fue remodelado en una área de 29 metros cuadrados," +
						" con placas estructurales en forma de U enclavadas en roca pura y enchapado en sillar, con una capacidad para 30 personas."
			}
			else -> {
				Toast.makeText(this, "No se encontro destino turistico", Toast.LENGTH_LONG).show()
				val intent = Intent(this, MainActivity::class.java)
				startActivity(intent)
				finish()
			}
		}
		// Cámara
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenada, 15f))
		// Eventos
		mMap.setOnMarkerClickListener(this)

	}

	override fun onMarkerClick(p0 : Marker) : Boolean {
		if (p0!!.equals(marcadorDestino)) {
			val intent = Intent(this, destinos::class.java)

			intent.putExtra("destino", destino)
			intent.putExtra("latitud", p0.getPosition().latitude.toString() + "")
			intent.putExtra("longitud", p0.getPosition().longitude.toString() + "")
			intent.putExtra("info", mensaje)
			intent.putExtra("img", img)
			startActivity(intent)
			return true
		}
		return false

	}

	fun moverCamara(view: View?){
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Plaza, 15f))
	}

	fun agregarMarcador(view: View?){
		mMap.addMarker(
			MarkerOptions().position(
				LatLng(
					mMap.cameraPosition.target.latitude,
					mMap.cameraPosition.target.longitude
				)
			)
				.title("Estas en la Plaza de Armas")
		)
	}

	override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
		mMap.setMapType(tipos_mapas[p2]);
	}


	override fun onNothingSelected(parent : AdapterView<*>?) {
		TODO("Not yet implemented")
	}

	private fun searchForLocation(query: String) {
		val geocoder = Geocoder(this)
		val addressList: List<Address> = geocoder.getFromLocationName(query, 1) !!

		if (addressList.isNotEmpty()) {
			val address = addressList[0]
			val latLng = LatLng(address.latitude, address.longitude)
			mMap.clear()
			mMap.addMarker(MarkerOptions().position(latLng).title(address.getAddressLine(0)))
			mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
		} else {
			Toast.makeText(this, "No se encontró el lugar", Toast.LENGTH_SHORT).show()
		}
	}


}