package poc.ishanjoshi.me.exploremonash.adapters

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import poc.ishanjoshi.me.exploremonash.R
import poc.ishanjoshi.me.exploremonash.model.Tour
import poc.ishanjoshi.me.exploremonash.utils.C
import poc.ishanjoshi.me.exploremonash.utils.C.PLACE_TO_START_INDEX
import poc.ishanjoshi.me.exploremonash.utils.C.SELECTED_TOUR_ID
import poc.ishanjoshi.me.exploremonash.view.activities.TourViewActivity
import poc.ishanjoshi.me.exploremonash.view.holders.TourViewHolder


class TourAdapter(optionsConfig: FirebaseRecyclerOptions<Tour>, context: Context) : FirebaseRecyclerAdapter<Tour, TourViewHolder>(optionsConfig) {

    val TAG = "TourAdapter"
    val applicationContext = context

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TourViewHolder {
        val mainView = LayoutInflater.from(p0.context).inflate(R.layout.layout_tour_card, p0, false)
        return TourViewHolder(mainView)
    }

    override fun onBindViewHolder(holder: TourViewHolder, position: Int, model: Tour) {
        holder.textHeading.text = model.name
        holder.textDescription.text = model.description

        Log.d(TAG, "Model recieved $model")


        val imageUrl = if (model.image != null) model.image else C.DEFAULT_MONASH_IMAGE

        //Load image with glide
        Glide.with(holder.imageView).load(imageUrl).into(holder.imageView)


        holder.itemView.setOnClickListener {

            Log.d(TAG, "Item clicked is $model")

            val placeToStart = model.place_start


            if (placeToStart != null) {

                //Only open if tour has places assigned
                val openTourView = Intent(holder.itemView.context, TourViewActivity::class.java)
                openTourView.flags = FLAG_ACTIVITY_NEW_TASK
                openTourView.putExtra(SELECTED_TOUR_ID, model.id)
                openTourView.putExtra(PLACE_TO_START_INDEX, placeToStart)

                applicationContext.startActivity(openTourView)

            } else {
                Toast.makeText(applicationContext, "Tour is empty", Toast.LENGTH_SHORT).show()
            }


        }
    }

}