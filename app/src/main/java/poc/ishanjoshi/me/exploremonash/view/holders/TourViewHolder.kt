package poc.ishanjoshi.me.exploremonash.view.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import poc.ishanjoshi.me.exploremonash.R

class TourViewHolder(mainView: View) : RecyclerView.ViewHolder(mainView) {

    val textHeading : TextView = mainView.findViewById(R.id.textTitle)
    val textDescription : TextView = mainView.findViewById(R.id.textDescription)
    val imageView : ImageView = mainView.findViewById(R.id.imageTourBanner) as ImageView


}
