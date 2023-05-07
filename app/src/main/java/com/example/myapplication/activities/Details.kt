package com.example.myapplication.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.models.Paymentmodel
import com.google.firebase.database.FirebaseDatabase

class Details : AppCompatActivity() {
    private lateinit var tvid:TextView
    private lateinit var tvnumber:TextView
    private lateinit var tvname:TextView
    private lateinit var tvmonth:TextView
    private lateinit var tvcvv:TextView


    private lateinit var update: Button
    private lateinit var delete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update)

        initView()
        setValuesToViews()

        update.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("payid").toString(),
                intent.getStringExtra("etnumber").toString()
            )
        }
        delete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("payid").toString()
            )
        }

    }
    private fun deleteRecord(
        id: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("payments").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "pay data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, Viewdata::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }


    }




    private fun initView() {
        tvid = findViewById(R.id.tvid)
        tvnumber = findViewById(R.id.tvnumber)
        tvname = findViewById(R.id.tvname)
        tvmonth = findViewById(R.id.tvmonth)
        tvcvv = findViewById(R.id.tvcvv)

        update = findViewById(R.id.update)
        delete = findViewById(R.id.delete)
    }
    private fun setValuesToViews() {
        tvid.text = intent.getStringExtra("payid")
        tvnumber.text = intent.getStringExtra("cardnumber")
        tvname.text = intent.getStringExtra("cardname")
        tvmonth.text = intent.getStringExtra("mmyy")
        tvcvv.text = intent.getStringExtra("cvv")
    }
    private fun openUpdateDialog(

        payid:String,
        etnumber:String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.updatedialog, null)

        mDialog.setView(mDialogView)

        val etnumber = mDialogView.findViewById<EditText>(R.id.etnumber)
        val etname = mDialogView.findViewById<EditText>(R.id.etname)
        val etmonth = mDialogView.findViewById<EditText>(R.id.etmonth)
        val etcvv = mDialogView.findViewById<EditText>(R.id.etcvv)
        val button8 = mDialogView.findViewById<Button>(R.id.button8)


        etnumber.setText(intent.getStringExtra("cardnumber").toString())
        etname.setText(intent.getStringExtra("cardname").toString())
        etmonth.setText(intent.getStringExtra("mmyy").toString())
        etcvv.setText(intent.getStringExtra("cvv").toString())


        mDialog.setTitle("Updating $etnumber Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

         button8.setOnClickListener {
            updatepayData(
                payid,
                etnumber.text.toString(),
                etname.text.toString(),
                etmonth.text.toString() ,
                etcvv.text.toString()
            )

            Toast.makeText(applicationContext, "pay Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
             tvnumber.text = intent.getStringExtra("cardnumber")
             tvname.text = intent.getStringExtra("cardname")
             tvmonth.text = intent.getStringExtra("mmyy")
             tvcvv.text = intent.getStringExtra("cvv")

            alertDialog.dismiss()
        }
    }

    private fun updatepayData(
        id: String,
        number: String,
        name :String,
        month: String,
        cvvv: String

    )
    {
        val dbRef = FirebaseDatabase.getInstance().getReference("payments").child(id)
        val payinfo = Paymentmodel(id, number, name, month,cvvv)
        dbRef.setValue(payinfo)
    }

}
