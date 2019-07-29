package poc.ishanjoshi.me.exploremonash.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

abstract class BaseActivity : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Initialize appwide components
        databaseReference = FirebaseDatabase.getInstance().reference

    }



}
