package poc.ishanjoshi.me.exploremonash.base;

import android.app.Application;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.FirebaseDatabase;
import poc.ishanjoshi.me.exploremonash.R;

public class BaseApplication extends Application {



    @Override
    public void onCreate() {
        super.onCreate();


        MobileAds.initialize(this, getString(R.string.admob_app_id));


        //---------------------------------
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        database.setPersistenceEnabled(true);
        database.getReference().child("places").keepSynced(true);
        database.getReference().child("tours").keepSynced(true);

        //---------------------------------

    }
}
