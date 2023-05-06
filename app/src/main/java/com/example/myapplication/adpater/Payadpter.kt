package com.example.myapplication.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.Paymentmodel

class Payadpter (private val payList: ArrayList<Paymentmodel>) :
    RecyclerView.Adapter<Payadpter.ViewHolder>() {

    private  var mListener: OnItemClickListener? =null

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.sucessfullyadded, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEmp = payList[position]
        holder.tvEmpName.text = currentEmp.cardname
    }

    override fun getItemCount(): Int {
        return payList.size
    }

    class ViewHolder(itemView: View, clickListener: OnItemClickListener?) : RecyclerView.ViewHolder(itemView) {

        val tvEmpName: TextView = itemView.findViewById(R.id.tvEmpName)

        init {
            clickListener?.let { listener ->
                itemView.setOnClickListener {
                    listener.onItemClick(adapterPosition)
                }
            }
        }
    }

    }

