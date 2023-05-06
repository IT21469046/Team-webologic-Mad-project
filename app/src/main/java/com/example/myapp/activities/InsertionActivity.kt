package com.example.myapp.activities

import android.content.ClipData.Item
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myapp.models.EmployeeModel
import com.example.myapp.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etItemName: EditText
    private lateinit var etItemPrice: EditText
    private lateinit var etItemQty: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etItemName = findViewById(R.id.etItemName)
        etItemPrice = findViewById(R.id.etItemPrice)
        etItemQty = findViewById(R.id.etItemQty)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {

        //getting values
        val ItemName = etItemName.text.toString()
        val ItemPrice = etItemPrice.text.toString()
        val ItemQty = etItemQty.text.toString()

        if (ItemName.isEmpty()) {
            etItemName.error = "Please Enter Name"
        }
        if (ItemPrice.isEmpty()) {
            etItemPrice.error = "Please enter Price"
        }
        if (ItemQty.isEmpty()) {
            etItemQty.error = "Please Enter Qty"
        }

        val ItemId = dbRef.push().key!!

        val employee = EmployeeModel(ItemId, ItemName, ItemPrice, ItemQty)

        dbRef.child(ItemId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Item Added successfully", Toast.LENGTH_LONG).show()

                etItemName.text.clear()
                etItemPrice.text.clear()
                etItemQty.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}