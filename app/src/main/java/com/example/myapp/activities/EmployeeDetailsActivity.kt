package com.example.myapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.LocusId
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.models.EmployeeModel
import com.example.myapp.R
import com.google.firebase.database.FirebaseDatabase

class EmployeeDetailsActivity : AppCompatActivity() {

    private lateinit var tvItemId: TextView
    private lateinit var tvItemName: TextView
    private lateinit var tvItemPrice: TextView
    private lateinit var tvItemQty: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("ItemId").toString(),
                intent.getStringExtra("ItemName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("ItemId").toString()
            )
        }

    }

    private fun initView() {
        tvItemId = findViewById(R.id.tvItemId)
        tvItemName = findViewById(R.id.tvItemName)
        tvItemPrice = findViewById(R.id.tvItemPrice)
        tvItemQty = findViewById(R.id.tvItemQty)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvItemId.text = intent.getStringExtra("ItemId")
        tvItemName.text = intent.getStringExtra("ItemName")
        tvItemPrice.text = intent.getStringExtra("ItemPrice")
        tvItemQty.text = intent.getStringExtra("ItemQty")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Item Details Deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun openUpdateDialog(
        ItemId: String,
        ItemName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etItemName = mDialogView.findViewById<EditText>(R.id.etItemName)
        val etItemPrice = mDialogView.findViewById<EditText>(R.id.etItemPrice)
        val etItemQty = mDialogView.findViewById<EditText>(R.id.etItemQty)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etItemName.setText(intent.getStringExtra("ItemName").toString())
        etItemPrice.setText(intent.getStringExtra("ItemPrice").toString())
        etItemQty.setText(intent.getStringExtra("ItemQty").toString())

        mDialog.setTitle("Updating $ItemName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                ItemId,
                etItemName.text.toString(),
                etItemPrice.text.toString(),
                etItemQty.text.toString()
            )

            Toast.makeText(applicationContext, "Employee Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvItemName.text = etItemName.text.toString()
            tvItemPrice.text = etItemPrice.text.toString()
            tvItemQty.text = etItemQty.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id: String,
        name: String,
        price: String,
        qty: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val empInfo = EmployeeModel(id, name, price, qty)
        dbRef.setValue(empInfo)
    }

}