package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapplication.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var gocard: Button
    private lateinit var gocash:Button
    private lateinit var viewdetails:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.payment)

        gocard =findViewById(R.id.gocard)
        gocash=findViewById(R.id.gocash)
        viewdetails=findViewById(R.id.viewdetails)


        gocard.setOnClickListener{
            val intent =Intent(this, Insertactivity::class.java)
            startActivity(intent)
        }

        viewdetails.setOnClickListener{
            val intent =Intent(this, Viewdata::class.java)
            startActivity(intent)
        }

        gocash.setOnClickListener{

        }


        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()
    }
}