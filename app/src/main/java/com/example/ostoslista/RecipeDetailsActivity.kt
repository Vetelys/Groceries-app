package com.example.ostoslista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecipeDetailsActivity : AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecipeDetailsAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipedetails)

        val recipeName = intent.getStringExtra("RecipeName")
        val db = DBHelper.getInstance(this)
        val ingredients: ArrayList<String> = db.getAllIngredients(recipeName.toString())

        val recyclerView = findViewById<RecyclerView>(R.id.ingredientRecyclerView)
        val recipeNameview = findViewById<TextView>(R.id.recipeNameTextView)
        val deleteButton = findViewById<Button>(R.id.deleteButton)

        recipeNameview.text = recipeName

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = RecipeDetailsAdapter(ingredients)
        recyclerView.adapter = adapter

        deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(this);
            builder.setMessage("Permanently delete recipe?")

            builder.setPositiveButton("Yes"){_, _ ->
                db.removeRecipe(recipeName.toString())
                finish()
            }
            builder.setNeutralButton("Cancel"){_, _ ->
                Toast.makeText(this, "Delete cancelled.", Toast.LENGTH_SHORT).show()
            }

            val alertDialog: AlertDialog = builder.create()

            alertDialog.setCancelable(true)
            alertDialog.show()

        }


    }
}