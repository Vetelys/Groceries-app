package com.example.ostoslista

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView



class ShoppingListAdapter(private val dataSet: ArrayList<String>) : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemName: TextView = itemView.findViewById(R.id.product_text)
        var checkBox: CheckBox = itemView.findViewById(R.id.product_checkbox)
        init{
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                //Toast.makeText(itemView.context, "You clicked on ${dataSet[position]}", Toast.LENGTH_SHORT).show()
            }
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    val db = DBHelper.getInstance(itemView.context)
                    val name = dataSet[adapterPosition]
                    db.removeProduct(name)
                    dataSet.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
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
        viewHolder.checkBox.isChecked = false
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return dataSet.size
    }

}