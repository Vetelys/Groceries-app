package com.example.ostoslista

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
//class CustomAdapter(private val dataSet: Array<String>, private val images: Array<Int>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>(){
class CustomAdapter(private val dataSet: Array<String>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemName: TextView
        //var itemImage: ImageView

        init{
            //itemImage = itemView.findViewById(R.id.recipe_image)
            itemName = itemView.findViewById(R.id.recipe_name)

            itemView.setOnClickListener {
                val position: Int = adapterPosition
                Toast.makeText(itemView.context, "You clicked on ${dataSet[position]}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemName.text = dataSet[position]
        //viewHolder.itemImage.setImageResource(images[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return dataSet.size
    }
}