package com.example.ostoslista

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
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
        /*
        TODO:
        Tässä ladataan reseptit sql tietokannasta
        ja annetaan ne adapterille
         */

        val names: Array<String> = resources.getStringArray(R.array.reseptit)
        val nimet = names.toMutableList()

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager


        adapter = CustomAdapter(nimet)
        recyclerView.adapter = adapter

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
            if (result?.resultCode == Activity.RESULT_OK) {
                if(result.data != null) {
                    val new_recipe = result.data!!.getStringExtra("Name").toString()
                    Log.d("Uusi resepti", new_recipe)
                    nimet.add(new_recipe)
                    adapter?.notifyItemInserted(nimet.size - 1)
                }
            }
        }


        val addButton = findViewById<Button>(R.id.add_button)

        addButton.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity::class.java)
            getContent.launch(intent)
        }

    }


}