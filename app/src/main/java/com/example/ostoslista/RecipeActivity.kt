package com.example.ostoslista

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RecipeActivity : AppCompatActivity(){
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<CustomAdapter.ViewHolder>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val recyclerView = findViewById<RecyclerView>(R.id.recipe_recyclerview)
        val db = DBHelper.getInstance(this)

        val recipes: ArrayList<String> = db.getRecipes()

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager


        adapter = CustomAdapter(recipes)
        recyclerView.adapter = adapter

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
            if (result?.resultCode == Activity.RESULT_OK) {
                if(result.data != null) {
                    val new_recipe = result.data!!.getStringExtra("Name").toString()
                    Log.d("Uusi resepti", new_recipe)
                    recipes.add(new_recipe)
                    adapter?.notifyItemInserted(recipes.size - 1)
                }
            }
        }


        val addButton = findViewById<Button>(R.id.add_button)

        addButton.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity::class.java)
            intent.putStringArrayListExtra("ExistingRecipes", recipes)
            getContent.launch(intent)
        }

    }


}