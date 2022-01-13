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
    private var recipes = arrayListOf<String>();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val recyclerView = findViewById<RecyclerView>(R.id.recipe_recyclerview)
        val db = DBHelper.getInstance(this)

        recipes = db.getRecipes()

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

    override fun onResume() {
        super.onResume();
        val db = DBHelper.getInstance(this);
        val dbRecipes : ArrayList<String> = db.getRecipes();
        db.close();
        if(dbRecipes.size > recipes.size){
            val difference = dbRecipes.toSet().minus(recipes.toSet());

            for(elem in difference){
                recipes.add(elem);
            }

            if(difference.isNotEmpty()){
                adapter?.notifyItemRangeInserted(recipes.size-difference.size, recipes.size);
            }
        }

        else if(dbRecipes.size < recipes.size){
            val difference = recipes.toSet().minus(dbRecipes.toSet());

            for(elem in difference){
                val index = recipes.indexOf(elem);
                recipes.removeAt(index);
                adapter?.notifyItemRemoved(index);
            }

        }

    }


}