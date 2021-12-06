package com.example.ostoslista

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recipesButton = findViewById<Button>(R.id.recipesButton)
        recipesButton.setOnClickListener {
            val recipesIntent = Intent(this, RecipeActivity::class.java)
            startActivity(recipesIntent)
            }
    }
}