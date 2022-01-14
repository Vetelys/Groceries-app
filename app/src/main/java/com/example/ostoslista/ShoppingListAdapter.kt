package com.example.ostoslista

import android.graphics.Color
import android.graphics.Paint
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class ShoppingListAdapter(private val dataSet: ArrayList<String>, private val checkedData: SparseBooleanArray) : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.product_text)
        val checkBox: CheckBox = itemView.findViewById(R.id.product_checkbox)

        init{

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    checkedData.put(adapterPosition, true)
                    itemName.setTextColor(Color.GRAY)
                    itemName.paintFlags = itemName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                }else{
                    checkedData.put(adapterPosition, false)
                    itemName.setTextColor(Color.WHITE)
                    itemName.paintFlags = itemName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.shoppinglist_layout, parent, false)
        return ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemName.text = dataSet[position]
        viewHolder.checkBox.isChecked = checkedData.get(position, false)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return dataSet.size
    }

}