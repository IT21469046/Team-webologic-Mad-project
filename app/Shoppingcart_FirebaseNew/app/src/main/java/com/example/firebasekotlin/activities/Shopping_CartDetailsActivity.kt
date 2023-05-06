package com.example.firebasekotlin.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasekotlin.R
import com.example.firebasekotlin.models.Shopping_cart_Model
import com.google.firebase.database.FirebaseDatabase

class Shopping_CartDetailsActivity:AppCompatActivity() {
    private lateinit var tvCartId:TextView
    private lateinit var tvItemName:TextView
    private lateinit var tvItemPrice:TextView
    private lateinit var tvItemQty:TextView
    private lateinit var tvItemDes:TextView
    private lateinit var btn_Update:Button
    private lateinit var btn_Delete:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_details)

        initView()
        setValuesToViews()

        btn_Update.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("ItemId").toString(),
                intent.getStringExtra("ItemName").toString()
            )
        }
        btn_Delete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("ItemId").toString()
            )
        }
    }

    private fun deleteRecord(
id:String
    )
    {
        val db_ref = FirebaseDatabase.getInstance().getReference("cart").child(id)
        val mTask=db_ref.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Shopping cart details deleted ",Toast.LENGTH_LONG).show()

            val intent=Intent(this,FetchingActivity::class.java)
            finish()
                startActivity(intent)
        }.addOnFailureListener{error->
            Toast.makeText(this,"Deleting error ${error.message} ",Toast.LENGTH_LONG).show()


        }
    }



    private fun openUpdateDialog(
        ItemId:String,
        ItemName:String
    ) {

        val mDialog=AlertDialog.Builder(this)
        val inflater=layoutInflater
        val mDialogView=inflater.inflate(R.layout.updatedialog,null)

        mDialog.setView(mDialogView)

        val edit_ItemName=mDialogView.findViewById<EditText>(R.id.edit_ItemName)
        val edit_ItemPrice=mDialogView.findViewById<EditText>(R.id.edit_ItemPrice)
        val  edit_ItemQty=mDialogView.findViewById<EditText>(R.id.edit_ItemQty)
        val edit_ItemDes=mDialogView.findViewById<EditText>(R.id.edit_ItemDes)
        val btnUpdateData=mDialogView.findViewById<Button>(R.id.btnUpdateData)

        edit_ItemName.setText(intent.getStringExtra("ItemName").toString())
        edit_ItemPrice.setText(intent.getStringExtra("ItemPrice").toString())
        edit_ItemQty.setText(intent.getStringExtra("ItemQty").toString())
        edit_ItemDes.setText(intent.getStringExtra("ItemDes").toString())

        mDialog.setTitle("Updating $ItemName Record")

        val alertDialog=mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            UpdateCartData(
                ItemId,
                edit_ItemName.text.toString(),
                edit_ItemPrice.text.toString(),
                edit_ItemQty.text.toString(),
                edit_ItemDes.text.toString()
            )
            Toast.makeText(applicationContext,"Shopping Cart Data Updated",Toast.LENGTH_LONG).show()
//we are setting updated data to text views
            tvItemName.text=edit_ItemName.text.toString()
            tvItemPrice.text=  edit_ItemPrice.text.toString()
            tvItemQty.text=edit_ItemQty.text.toString()
            tvItemDes.text= edit_ItemDes.text.toString()

            alertDialog.dismiss()

        }


    }

    private fun UpdateCartData(
        id: String,
        name:String,
        price:String,
        qty:String,
        des:String
    ){

        //update firebase data
       val db_ref = FirebaseDatabase.getInstance().getReference("cart").child(id)
        val cartInfo=Shopping_cart_Model(id,name,price,qty,des)
        db_ref.setValue(cartInfo)
    }

    private fun setValuesToViews() {
        tvCartId.text=intent.getStringExtra("ItemId")
        tvItemName.text=intent.getCharSequenceExtra("ItemName")
        tvItemPrice.text=intent.getStringExtra("ItemPrice")
        tvItemQty.text=intent.getStringExtra("ItemQty")
        tvItemDes.text=intent.getStringExtra("ItemDes")
    }

    private fun initView() {
        tvCartId=findViewById(R.id.tvCartId)
        tvItemName=findViewById(R.id.tvItemName)
        tvItemPrice=findViewById(R.id.tvItemPrice)
        tvItemQty=findViewById(R.id.tvItemQty)
        tvItemDes=findViewById(R.id.tvItemDes)


        btn_Update=findViewById(R.id.btn_Update)
        btn_Delete=findViewById((R.id.btn_Delete))
    }


}