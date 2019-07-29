package poc.ishanjoshi.me.exploremonash.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import poc.ishanjoshi.me.exploremonash.R
import poc.ishanjoshi.me.exploremonash.interfaces.DataChange
import poc.ishanjoshi.me.exploremonash.interfaces.PlaceClick
import poc.ishanjoshi.me.exploremonash.model.Place
import poc.ishanjoshi.me.exploremonash.utils.C.DEFAULT_MONASH_IMAGE
import poc.ishanjoshi.me.exploremonash.view.holders.PlaceViewHolder


//https://stackoverflow.com/questions/41576554/how-to-implement-firebase-with-viewpager






class PlaceAdapter(optionsConfig: FirebaseRecyclerOptions<Place>, context: Context) :
    FirebaseRecyclerAdapter<Place, PlaceViewHolder>(optionsConfig) {

    val TAG = "PlacesSwipe"
    val applicationContext = context




    lateinit var dataChange : DataChange
    lateinit var placeClick: PlaceClick



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PlaceViewHolder {
        val mainView = LayoutInflater.from(p0.context).inflate(R.layout.layout_place_card, p0, false)
        return PlaceViewHolder(mainView)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int, model: Place) {
        holder.textHeading.text = model.name
        holder.textDescription.text = model.description

        val imageUrl = if (model.image != null) model.image else DEFAULT_MONASH_IMAGE

        //Load image with glide
        Glide.with(holder.imageView).load(imageUrl).into(holder.imageView)

        Log.d(TAG, "Model recieved $model")


        holder.itemView.setOnClickListener {
            Log.d(TAG, "Item clicked is $model")
            passOnClickEvent(model)
        }
    }




    fun setDataChangeListener(dataChange: DataChange) {
        this.dataChange = dataChange
    }

    fun setPlaceClickListener(placeClick: PlaceClick) {
        this.placeClick = placeClick
    }


    override fun onDataChanged() {
        if (dataChange != null) {
            dataChange.onDataChange()
        }
    }

    private fun passOnClickEvent(place: Place) {
        if (placeClick != null)
            placeClick.onPlaceClick(place)
    }
}