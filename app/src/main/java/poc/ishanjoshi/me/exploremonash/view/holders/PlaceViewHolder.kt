package poc.ishanjoshi.me.exploremonash.view.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import poc.ishanjoshi.me.exploremonash.R

class PlaceViewHolder(mainView: View) : RecyclerView.ViewHolder(mainView) {

    val textHeading : TextView = mainView.findViewById(R.id.textPlaceTitle)
    val textDescription : TextView = mainView.findViewById(R.id.textPlaceDescription)
    val imageView : ImageView = mainView.findViewById(R.id.imagePlacePhoto) as ImageView


}
