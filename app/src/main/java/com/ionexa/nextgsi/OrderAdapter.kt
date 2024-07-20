package com.ionexa.nextgsi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

data class Order(val imageUrl: String, val itemName: String, val itemDetails: String, val totalCost: String)

class OrderAdapter(private val orderList: List<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewOrder)
        val itemName: TextView = itemView.findViewById(R.id.textViewItemName)
        val itemDetails: TextView = itemView.findViewById(R.id.textViewItemDetails)
        val totalCost: TextView = itemView.findViewById(R.id.textViewTotalCost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.itemName.text = order.itemName
        holder.itemDetails.text = order.itemDetails
        holder.totalCost.text = order.totalCost

        // Load image using Glide


        Picasso.get().load(order.imageUrl).into(holder.imageView);
    }

    override fun getItemCount() = orderList.size
}
