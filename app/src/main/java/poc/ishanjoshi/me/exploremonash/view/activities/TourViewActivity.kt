package poc.ishanjoshi.me.exploremonash.view.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.Query
import kotlinx.android.synthetic.main.activity_tour_view.*
import poc.ishanjoshi.me.exploremonash.R
import poc.ishanjoshi.me.exploremonash.adapters.PlaceAdapter
import poc.ishanjoshi.me.exploremonash.base.BaseActivity
import poc.ishanjoshi.me.exploremonash.interfaces.DataChange
import poc.ishanjoshi.me.exploremonash.interfaces.PlaceClick
import poc.ishanjoshi.me.exploremonash.model.Place
import poc.ishanjoshi.me.exploremonash.utils.C
import poc.ishanjoshi.me.exploremonash.utils.C.PLACE_TO_START_INDEX
import poc.ishanjoshi.me.exploremonash.utils.C.SELECTED_TOUR_ID
import poc.ishanjoshi.me.exploremonash.utils.MapManager
import poc.ishanjoshi.me.exploremonash.utils.distanceTo
import poc.ishanjoshi.me.exploremonash.utils.getEndFrom
import poc.ishanjoshi.me.exploremonash.view.helper.PlaceSnapHelper
import poc.ishanjoshi.me.exploremonash.view.helper.getSnapPosition


class TourViewActivity : BaseActivity(), OnMapReadyCallback, DataChange, PlaceClick {


    private lateinit var mMap: GoogleMap
    private lateinit var tourAdapter: PlaceAdapter
    private lateinit var mapManager: MapManager
    private lateinit var layoutManager: LinearLayoutManager


    val TAG = "TourViewActivity"
    var newPosition = 0
    val MY_PERMISSIONS_REQUEST_LOCATION = 56789
    val RUN_DELAY: Long = 500
    val CURRENT_LOCATION_INDEX_TAG = "currentLocationIndex"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tour_view)


        //Set back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //Initialize the map
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        //Prepare the data array from intent
        val selectedTourId = intent.getStringExtra(SELECTED_TOUR_ID)
        val placesStartPoint = intent.getStringExtra(PLACE_TO_START_INDEX)

        Log.d(TAG, "Starting place index is $placesStartPoint")

        //SETUP the recyclerview
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerPlacesInTour.setHasFixedSize(true)
        recyclerPlacesInTour.layoutManager = layoutManager

        val snapHelper = PlaceSnapHelper()
        snapHelper.attachToRecyclerView(recyclerPlacesInTour)


        val query: Query =
            databaseReference.child("places").orderByKey().startAt(placesStartPoint).endAt(getEndFrom(placesStartPoint))


        val optionsConfig = FirebaseRecyclerOptions.Builder<Place>()
            .setQuery(query, Place::class.java)
            .setLifecycleOwner(this)
            .build()

        tourAdapter = PlaceAdapter(optionsConfig, applicationContext)
        tourAdapter.setDataChangeListener(this)
        tourAdapter.setPlaceClickListener(this)
        recyclerPlacesInTour.adapter = tourAdapter



        recyclerPlacesInTour.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    newPosition = snapHelper.getSnapPosition(recyclerPlacesInTour)
                    Log.d(TAG, "New position is ${newPosition}")
                    onItemSnapChange(newPosition)
                }
            }
        })


    }


    override fun onDataChange() {
        Log.d(TAG, "Data has changed")
        mapManager.drawPlaceMarkers(tourAdapter.snapshots)
    }


    fun onItemSnapChange(index: Int) {
        Log.d(TAG, "onItemSnapChange called with $index")
        try {

            val currentPlace = tourAdapter.getItem(index)
            if (currentPlace.name != "Welcome") {
                mapManager.snapToCurrentListItem(currentPlace)
            } else {
                //Could display something fancy?
            }

        } catch (e: IndexOutOfBoundsException) {
            Log.v(TAG, "Activity closing and tried to find a place in adapter")
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap



        mapManager = MapManager(mMap, applicationContext)
        mapManager.setDefaults()
        mapManager.applyCustomStyle()



        onLocationPermissionGrantedManager()
    }


    fun onLocationPermissionGrantedManager() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {

            //Not doing this through mapmanager as it requires this check
            mMap.isMyLocationEnabled = true

            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10f,
                object : LocationListener {
                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

                    override fun onProviderEnabled(provider: String?) {}

                    override fun onProviderDisabled(provider: String?) {}

                    override fun onLocationChanged(location: Location?) {
                        mapManager.checkIfNearbyALocation(tourAdapter.snapshots, location)

                        if (location != null) {
                            val currentLatLng = LatLng(location.latitude, location.longitude)
                            //Also check if they're within bounds of Monash Campus
                            if (currentLatLng.distanceTo(C.MONASH_CENTER) > C.RADIUS_CIRCLE_THRESHOLD) {
                                //User is not on campus
                                textMessageAlert.text = getString(R.string.text_not_on_campus)
                                textMessageAlert.setTextColor(Color.GRAY)
                                textMessageAlert.visibility = View.VISIBLE


                            } else {
                                textMessageAlert.visibility = View.GONE
                            }
                        }
                    }

                })

        } else {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onLocationPermissionGrantedManager();
                } else {
                    Toast.makeText(applicationContext, "Cannot get location", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                //Go back when the back arrow in toolbar is clicked
                finish()
            }
            R.id.buttonLeftControl -> {

                if (newPosition > 0) {
                    onItemSnapChange(newPosition - 1)
                    recyclerPlacesInTour.smoothScrollToPosition(newPosition - 1)
                }

            }
            R.id.buttonRightControl -> {
                onItemSnapChange(newPosition + 1)
                recyclerPlacesInTour.smoothScrollToPosition(newPosition + 1)
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPlaceClick(place: Place) {

        if (place.name != "Welcome") {

            val openDetailedViewActivity = Intent(this, PlaceDetailedViewActivity::class.java)
            openDetailedViewActivity.putExtra(C.SELECTED_PLACE_DETAILED_VIEW, place.id)
            openDetailedViewActivity.putExtra(C.SELECTED_PLACE_NAME_DETAILED_VIEW, place.name)
            openDetailedViewActivity.setFlags(FLAG_ACTIVITY_NEW_TASK)
            applicationContext.startActivity(openDetailedViewActivity)

        } else {
            Log.e(TAG, "Tried clicking the welcome event. Will NOT be processed further.")

        }
    }


    override fun onResume() {
        super.onResume()
        if (newPosition != -1) {
            Log.d(TAG, "Lifecycle -- onResume, index position is $newPosition")

            Handler().postDelayed({
                recyclerPlacesInTour.smoothScrollToPosition(newPosition)
            }, RUN_DELAY)


        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_tour_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
