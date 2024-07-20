package com.ionexa.nextgsi

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference
import kotlin.properties.Delegates

class OrderHistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var orderList: MutableList<Order>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewOrders)
        recyclerView.layoutManager = LinearLayoutManager(this)
        orderList = mutableListOf()
        orderAdapter = OrderAdapter(orderList)
        recyclerView.adapter = orderAdapter

        // Add some local data for testing
        addLocalData()


        val totalSum = orderList.sumOf {
            Integer.parseInt(it.totalCost.replace("₹", "").trim())
        }

        // Find the TextView by its ID
        val totalCostTextView: TextView = findViewById(R.id.totalCost)

        // Set the totalSum value as the text of the TextView
        totalCostTextView.text = "₹$totalSum"
    }

    private fun addLocalData() {
        val orders = listOf(
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 1", "Details of Item 1", "₹100"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 2", "Details of Item 2", "₹200"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 3", "Details of Item 3", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 4", "Details of Item 4", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 5", "Details of Item 5", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 6", "Details of Item 6", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 7", "Details of Item 7", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 8", "Details of Item 8", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 9", "Details of Item 9", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 10", "Details of Item 10", "₹300"),
            Order("https://i.imgur.com/tGbaZCY.jpg", "Item 11", "Details of Item 11", "₹400")
        )
        orderList.addAll(orders)
        orderAdapter.notifyDataSetChanged()


    }
}


/*

class OrderHistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var orderList: MutableList<Order>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewOrders)
        recyclerView.layoutManager = LinearLayoutManager(this)
        orderList = mutableListOf()
        orderAdapter = OrderAdapter(orderList)
        recyclerView.adapter = orderAdapter

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance().getReference("orders")

        // Fetch orders from Firebase
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                orderList.clear()
                for (orderSnapshot in snapshot.children) {
                    val order = orderSnapshot.getValue(Order::class.java)
                    if (order != null) {
                        orderList.add(order)
                    }
                }
                orderAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }
}

 */