package com.example.firebasekotlin.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasekotlin.R
import com.example.firebasekotlin.models.Shopping_cart_Model

class CartAdapter(private val cartList: ArrayList<Shopping_cart_Model>):RecyclerView.Adapter<CartAdapter.ViewHolder>(){
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.cart_list_item,parent,false)
        return ViewHolder(itemView,mListener)

    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        //get the name of current cart item
        val current_Cart_Item=cartList[position]
        holder.tvCartItem_Name.text=current_Cart_Item.ItemName
    }

    class ViewHolder(itemView: View,clickListener: onItemClickListener):RecyclerView.ViewHolder(itemView) {
val tvCartItem_Name:TextView=itemView.findViewById(R.id.tvCartItem_Name)
init {
    itemView.setOnClickListener{
        clickListener.onItemClick(adapterPosition)
    }
}
    }

    override fun getItemCount(): Int {
        return cartList.size
    }


}