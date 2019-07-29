package poc.ishanjoshi.me.exploremonash

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.ads.AdRequest
import com.google.firebase.database.Query
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_tour_view.*
import poc.ishanjoshi.me.exploremonash.adapters.TourAdapter
import poc.ishanjoshi.me.exploremonash.base.BaseActivity
import poc.ishanjoshi.me.exploremonash.model.Tour
import android.content.Intent
import poc.ishanjoshi.me.exploremonash.view.activities.AboutActivity


class MainActivity :  BaseActivity() {

    val TAG = "MainActivityDebug"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //----------------------------------------------------------------------------------------
        //Load up all the tours in the recycler adapter

        val query : Query = databaseReference.child("tours").orderByChild("order")

        val optionsConfig = FirebaseRecyclerOptions.Builder<Tour>()
            .setQuery(query, Tour::class.java)
            .setLifecycleOwner(this)
            .build()

        val tourAdapter = TourAdapter(optionsConfig, applicationContext)

        recyclerAllTours.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = tourAdapter
        }


        //----------------------------------------------------------------------------------------
        //Load ad
        val adRequest = AdRequest.Builder().build()
        adViewMainPage.loadAd(adRequest)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }



    fun shareApp() {
        val shareBody = resources.getString(R.string.share_message_body)
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Explore Monash App")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, resources.getString(R.string.share_using)))

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.buttonShare -> {
                shareApp()
            }
            R.id.buttonAbout -> {
                startActivity(Intent(this, AboutActivity::class.java))
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
