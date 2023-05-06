package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adpater.Payadpter
import com.example.myapplication.models.Paymentmodel
import com.google.firebase.database.*

class Viewdata : AppCompatActivity() {

    private lateinit var rvEmp: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var payList:ArrayList<Paymentmodel>

    private lateinit var dbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.paymentsuccessfull)

        rvEmp=findViewById(R.id.rvEmp)
        rvEmp.layoutManager = LinearLayoutManager(this)
        rvEmp.setHasFixedSize(true)
        tvLoadingData =findViewById(R.id.tvLoadingData)

        payList = arrayListOf<Paymentmodel>()

        getPaydata()

        }

        private fun getPaydata(){
            rvEmp.visibility =View.GONE
            tvLoadingData.visibility =View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("payments")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("Viewdata", "onDataChange: Data snapshot received")
                payList.clear()
                if (snapshot.exists()) {
                    for (paySnap in snapshot.children) {
                        val payData = paySnap.getValue(Paymentmodel::class.java)
                        payList.add(payData!!)
                    }
                    val mAdapeter= Payadpter(payList)
                    rvEmp.adapter = mAdapeter

                    mAdapeter.setOnItemClickListener(object : Payadpter.OnItemClickListener{
                        override fun onItemClick(position: Int){
                            val intent = Intent(this@Viewdata,Details::class.java)

                            intent.putExtra("payid" ,payList[position].payid)
                            intent.putExtra("cardnumber" ,payList[position].cardnumber)
                            intent.putExtra("cardname" ,payList[position].cardname)
                            intent.putExtra("mmyy" ,payList[position].mmyy)
                            intent.putExtra("cvv" ,payList[position].cvv)

                            startActivity(intent)

                        }
                    })

                    rvEmp.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Viewdata", "onCancelled: Database error occurred: " + error.message)
            }

            })
    }
}