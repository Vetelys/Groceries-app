package com.example.ostoslista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.random.Random.Default.nextInt

class RecipeActivity : AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<CustomAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val recyclerView = findViewById<RecyclerView>(R.id.recipe_recyclerview)

        val names: Array<String> = resources.getStringArray(R.array.reseptit)

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        //adapter = CustomAdapter(names, images)
        adapter = CustomAdapter(names)
        recyclerView.adapter = adapter


        val addButton = findViewById<Button>(R.id.add_button)
        addButton.setOnClickListener {
            val dialog = AddProductDialogFragment()
            dialog.show(supportFragmentManager, "customDialog")
        }
        /*
        Tässä ladataan reseptit sql tietokannasta
        lisätään ne napin yläpuolelle näkyviin
        ja siitä voidaan valita, avata ja poistaa reseptejä
         */

    }
}