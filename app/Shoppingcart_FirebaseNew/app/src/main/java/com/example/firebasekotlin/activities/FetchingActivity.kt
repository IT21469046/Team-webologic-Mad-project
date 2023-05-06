package com.example.firebasekotlin.activities
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasekotlin.R
import com.example.firebasekotlin.adapters.CartAdapter
import com.example.firebasekotlin.models.Shopping_cart_Model
import com.google.firebase.database.*

class FetchingActivity : AppCompatActivity() {

    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var cartList:ArrayList<Shopping_cart_Model>
    private lateinit var  db_ref:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        cartRecyclerView = findViewById(R.id.rvCart)
        cartRecyclerView.layoutManager = LinearLayoutManager(this)
        cartRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        cartList = arrayListOf<Shopping_cart_Model >()

        get_Shopping_Cart_Data()

    }

    private fun get_Shopping_Cart_Data() {

        cartRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE//show loading data text view

        //create database ref
        db_ref = FirebaseDatabase.getInstance().getReference("cart")

        db_ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cartList.clear() //to avoid duplicate
                if (snapshot.exists()){
                    for (cartSnap in snapshot.children){
                        val cartData = cartSnap.getValue(Shopping_cart_Model::class.java)//get all records of shopping cart class
                        cartList.add(cartData!!)
                    }
                    val mAdapter = CartAdapter(cartList)
                    cartRecyclerView.adapter = mAdapter



                    cartRecyclerView.visibility=    View.VISIBLE
                    tvLoadingData.visibility=View.GONE

                    mAdapter.setOnItemClickListener(object : CartAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingActivity, Shopping_CartDetailsActivity::class.java)

                            //put extra data
                            intent.putExtra("Cart Item Id", cartList[position].ItemId)
                            intent.putExtra("Cart Item Name", cartList[position].ItemName)
                            intent.putExtra("Cart Item Price", cartList[position].ItemPrice)
                            intent.putExtra("Cart Item Quantity", cartList[position].ItemQty)
                            intent.putExtra("Cart Item Description", cartList[position].ItemDes)

                            startActivity(intent)
                        }

                    })

                    cartRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })





    }
}

