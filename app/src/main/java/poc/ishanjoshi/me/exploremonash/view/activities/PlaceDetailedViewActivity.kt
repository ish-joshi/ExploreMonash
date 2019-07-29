package poc.ishanjoshi.me.exploremonash.view.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_place_detailed_view.*
import kotlinx.android.synthetic.main.content_place_detailed_view.*
import poc.ishanjoshi.me.exploremonash.R
import poc.ishanjoshi.me.exploremonash.base.BaseActivity
import poc.ishanjoshi.me.exploremonash.utils.C

class PlaceDetailedViewActivity : BaseActivity() {

    val TAG = "PlaceDetailedViewAct"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_detailed_view)
        setSupportActionBar(toolbarPlaceDetailed)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Get the place selected id and load it into here
        val placeIdSelected = intent.getStringExtra(C.SELECTED_PLACE_DETAILED_VIEW)
        val placeNameSelected = intent.getStringExtra(C.SELECTED_PLACE_NAME_DETAILED_VIEW)

        supportActionBar?.title = placeNameSelected

        val firebasePlaceQuery = databaseReference.child("places").child(placeIdSelected)

        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, "Database error occured $p0")
            }

            override fun onDataChange(p0: DataSnapshot) {
                val imageUrl = p0.child("image").value
                Glide.with(applicationContext).load(imageUrl).into(imageBanner)

                val longDescription : String? = p0.child("longDescription").value.toString()
                if (longDescription != null && longDescription != "null") {
                    textLongContent.text = longDescription
                } else {
                    textLongContent.text = getString(R.string.no_long_description_available)
                }
            }
        }

        firebasePlaceQuery.addListenerForSingleValueEvent(valueEventListener)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
