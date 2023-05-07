package com.example.firebasekotlin.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasekotlin.R
import com.example.firebasekotlin.models.Shopping_cart_Model
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase




class InsertionActivity: AppCompatActivity() {
    private lateinit var edit_ItemName: EditText
    private lateinit var edit_ItemPrice: EditText
    private lateinit var edit_ItemQty: EditText
    private lateinit var edit_ItemDes: EditText

    private lateinit var btnSave_Data:Button
    private lateinit var db_ref:DatabaseReference
//val firebase:DatabaseReference =FirebaseDatabase.getInstance().getReference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        edit_ItemName = findViewById(R.id.edit_ItemName)
        edit_ItemPrice = findViewById(R.id.edit_ItemPrice)
        edit_ItemQty = findViewById(R.id.edit_ItemQty)
        edit_ItemDes = findViewById(R.id.edit_ItemDes)
        btnSave_Data = findViewById(R.id.btnSave_Data)

        db_ref = FirebaseDatabase.getInstance().getReference("cart")

        btnSave_Data.setOnClickListener {
            saveShopping_cart_Data()
        }
    }

    private fun saveShopping_cart_Data() {

        //getting values to insert ,convert to string input

        val ItemName = edit_ItemName.text.toString()
        val ItemPrice = edit_ItemPrice.text.toString()
        val ItemQty = edit_ItemQty.text.toString()
        val ItemDes = edit_ItemDes.text.toString()

        //validation of inserting data
        //when edit text is empty show this error

        if (ItemName.isEmpty()) {
            edit_ItemName.error = "Please enter cart item name"
        }
        if (ItemPrice.isEmpty()) {
            edit_ItemPrice.error = "Please enter cart item price"
        }
        if (ItemQty.isEmpty()) {
            edit_ItemQty.error = "Please enter cart item quantity"
        }
        if (ItemDes.isEmpty()) {
            edit_ItemDes.error = "Please enter cart item description"
        }

        //when entering data avoid overriding by passing unique id of cart item data

        val ItemId = db_ref.push().key!!

//pass data into model
        val Shopping_cart = Shopping_cart_Model(ItemId, ItemName, ItemPrice, ItemQty, ItemDes)

        //Put data into firebase
        //create child by using id
        db_ref.child(ItemId).setValue(Shopping_cart)
            .addOnCompleteListener {
                Toast.makeText(this, "Data successfully inserted", Toast.LENGTH_LONG).show()
                edit_ItemName.text.clear()
                edit_ItemPrice.text.clear()
                edit_ItemQty.text.clear()
                edit_ItemDes.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }
}