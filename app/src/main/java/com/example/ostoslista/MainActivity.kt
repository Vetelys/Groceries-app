package com.example.ostoslista

import com.example.tools.showKeyBoard
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(){
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>? = null
    private var shoppingListData = arrayListOf<String>();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recipesButton = findViewById<Button>(R.id.recipesButton)
        val editButton = findViewById<Button>(R.id.editProduct_button)
        val addButton = findViewById<Button>(R.id.addProduct_button)
        val productText = findViewById<EditText>(R.id.newProduct_text)

        val recyclerView = findViewById<RecyclerView>(R.id.shoppinglist_recyclerview)
        val db = DBHelper.getInstance(this)

        shoppingListData = db.getAllProducts()


        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = ShoppingListAdapter(shoppingListData)
        recyclerView.adapter = adapter

        recipesButton.setOnClickListener {
            val recipesIntent = Intent(this, RecipeActivity::class.java)
            startActivity(recipesIntent)
            }

        addButton.setOnClickListener {
            when (val product = productText.text.toString()) {
                "" -> {
                    Toast.makeText(this, "Add product name before adding it to the list", Toast.LENGTH_SHORT).show()
                }
                in shoppingListData -> {
                    Toast.makeText(this, "Product is already on the list", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    shoppingListData.add(product)
                    adapter?.notifyItemInserted(shoppingListData.size - 1)
                    db.insertProduct(product)
                    productText.text.clear()
                }
            }
        }

        editButton.setOnClickListener {
            productText.requestFocus()
            showKeyBoard(findViewById<View>(R.id.content).rootView)
        }
    }

    override fun onResume() {
        super.onResume();
        //check if any new ingredients were added while away from mainactivity
        val db = DBHelper.getInstance(this);
        val shopListElems : ArrayList<String> = db.getAllProducts();
        db.close();

        val difference = shopListElems.toSet().minus(shoppingListData.toSet());

        for(elem in difference){
            shoppingListData.add(elem);
        }

        if(difference.isNotEmpty()){
            adapter?.notifyItemRangeInserted(shoppingListData.size-difference.size, shoppingListData.size);
        }

    }

}