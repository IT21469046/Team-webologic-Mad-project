package com.example.firebasekotlin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.content.Intent
import com.example.firebasekotlin.R


class MainActivity : AppCompatActivity() {

    private lateinit var btnInsertData :Button
    private lateinit var btnFetchData :Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       //integrate firebase dependencies

       val firebase:DatabaseReference =FirebaseDatabase.getInstance().getReference()

        btnInsertData=findViewById(R.id.btnInsertData)
        btnFetchData=findViewById(R.id.btnFetchData)

        //navigate user to inserting activity
        btnInsertData.setOnClickListener{
val intent=Intent(this, InsertionActivity::class.java)
            startActivity(intent)
        }

        //navigate user to fetching activity
        btnFetchData.setOnClickListener{
            val intent = Intent(this, FetchingActivity::class.java)
            startActivity(intent)
        }
    }
}