package poc.ishanjoshi.me.exploremonash.utils

import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.SphericalUtil
import poc.ishanjoshi.me.exploremonash.R
import poc.ishanjoshi.me.exploremonash.model.Place
import android.R.attr.y
import android.R.attr.x
import android.view.Display
import android.widget.Toast
import com.google.android.gms.maps.model.*


fun LatLng.distanceTo(a: LatLng) : Double  {
    return SphericalUtil.computeDistanceBetween(this, a)
}


fun getDistance(a: LatLng, b: LatLng) : Double {
    return a.distanceTo(b)
}

class MapManager(googleMap: GoogleMap, context: Context) {

    val mMap = googleMap
    val applicationContext = context

    val TAG = "MapManager"
    val visitedTracker : HashMap<String, Boolean> = HashMap()

    fun drawPlaceMarkers(placeList: List<Place>) {

        mMap.clear()

        placeList.forEach  {

            val position = it.latLng
            val marker = MarkerOptions().title(it.name).position(position)

            val hasBeenVisited = visitedTracker.get(it.id) != null

            if (hasBeenVisited) {
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            } else {
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            }

            mMap.addMarker(marker)

        }
    }

    fun setDefaults() {
        //Set map defaults
        mMap.moveCamera(CameraUpdateFactory.newLatLng(C.MONASH_CENTER))
        mMap.setMinZoomPreference(C.DEFAULT_ZOOM)
    }

    fun applyCustomStyle() {
        //------------------------------------------------------------------------------------------------------
        //Apply custom styles
        val success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(applicationContext, R.raw.maps_thick_road_mode))
        if (!success) {
            Log.e(TAG, "Style parsing failed.")
        }
    }



    fun snapToCurrentListItem(place: Place) {

        mMap.setPadding(0, 0, 0, applicationContext.resources.getInteger(R.integer.map_offset_bottom))
        val currentPlaceLatLng = place.latLng
        mMap.animateCamera(CameraUpdateFactory.newLatLng(currentPlaceLatLng))

    }

    fun checkIfNearbyALocation(snapshots: List<Place>, location: Location?) {

        Log.d(TAG, "Checking if current location is nearby places")


        if (location != null) {

            val currentLatLng = LatLng(location.latitude, location.longitude)
            var changed = false

            snapshots.forEach {

                if (currentLatLng.distanceTo(it.latLng) < C.NEARBY_PLACE_THRESHOLD) {
                    visitedTracker[it.id] = true
                    changed = true
                }
            }

            if (changed)  { drawPlaceMarkers(snapshots) }


        }

    }




}