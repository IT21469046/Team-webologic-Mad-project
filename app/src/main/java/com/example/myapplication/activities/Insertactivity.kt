package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.models.Paymentmodel
import com.example.myapplication.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Insertactivity : AppCompatActivity() {

    private lateinit var editTextTextPersonName :EditText
    private lateinit var editTextTextPersonName2:EditText
    private lateinit var editTextTextPersonName3:EditText
    private lateinit var editTextTextPersonName4:EditText
    private lateinit var savecardindb:Button
    private lateinit var save:Button


    private lateinit var dbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.carddetails)

        editTextTextPersonName =findViewById(R.id.editTextTextPersonName)
        editTextTextPersonName2 =findViewById(R.id.editTextTextPersonName2)
        editTextTextPersonName3 =findViewById(R.id.editTextTextPersonName3)
        editTextTextPersonName4 =findViewById(R.id.editTextTextPersonName4)
        savecardindb =findViewById(R.id.savecardindb)
        save = findViewById(R.id.save)

        dbRef = FirebaseDatabase.getInstance().getReference("payments")

        savecardindb.setOnClickListener{
            savepayments()
        }
        save.setOnClickListener{
            val intent = Intent(this, Viewdata::class.java)
            startActivity(intent)
        }
    }

    private fun savepayments(){

        val cardnumber =editTextTextPersonName.text.toString()
        val cardname = editTextTextPersonName2.text.toString()
        val mmyy= editTextTextPersonName3.text.toString()
        val cvv = editTextTextPersonName4.text.toString()

        if (cardnumber.isEmpty()){
            editTextTextPersonName.error ="please enter cardnumber"
        }
        if (cardname.isEmpty()){
            editTextTextPersonName2.error ="please enter cardname"
        }
        if (mmyy.isEmpty()){
            editTextTextPersonName3.error ="please enter card month and year"
        }
        if (cvv.isEmpty()){
            editTextTextPersonName4.error ="please enter cvv"
        }

        val payid = dbRef.push().key!!

        val payment = Paymentmodel(payid, cardnumber, cardname, mmyy,cvv)

        dbRef.child(payid).setValue(payment)
            .addOnCompleteListener {
                Toast.makeText(this ,"data inserted succes" , Toast.LENGTH_SHORT).show()

                editTextTextPersonName.text.clear()
                editTextTextPersonName2.text.clear()
                editTextTextPersonName3.text.clear()
                editTextTextPersonName4.text.clear()

            }.addOnFailureListener{ err ->
                Toast.makeText( this ,"error ${err.message}" , Toast.LENGTH_SHORT).show()
            }

    }
}