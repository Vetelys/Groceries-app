package com.example.ostoslista

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(){
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recipesButton = findViewById<Button>(R.id.recipesButton)
        val editButton = findViewById<Button>(R.id.editProduct_button)
        val addButton = findViewById<Button>(R.id.addProduct_button)
        val productText = findViewById<EditText>(R.id.newProduct_text)

        val recyclerView = findViewById<RecyclerView>(R.id.shoppinglist_recyclerview)

        val ostokset: Array<String> = resources.getStringArray(R.array.ostoslista)
        val ostoslista = ostokset.toMutableList()

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = ShoppingListAdapter(ostoslista)
        recyclerView.adapter = adapter

        recipesButton.setOnClickListener {
            val recipesIntent = Intent(this, RecipeActivity::class.java)
            startActivity(recipesIntent)
            }

        addButton.setOnClickListener {
            val tuote = productText.text.toString()
            ostoslista.add(tuote)
            adapter?.notifyItemInserted(ostoslista.size - 1)

        }

        editButton.setOnClickListener {
            productText.requestFocus()
        }
    }

}