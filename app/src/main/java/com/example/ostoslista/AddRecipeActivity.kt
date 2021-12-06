package com.example.ostoslista


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager

//https://www.youtube.com/watch?v=SkFcDWt9GV4

class AddRecipeActivity : AppCompatActivity() {

    private var index: Int = 1
    private val ingredients = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)
        val recipeName = findViewById<EditText>(R.id.recipeName)
        recipeName.requestFocus()
        val textLayout = findViewById<LinearLayout>(R.id.recipeIngredientLayout)
        val addIngredientText = findViewById<EditText>(R.id.addIngredientEditText)
        val cancelBtn = findViewById<Button>(R.id.cancelRecipeButton)
        val addBtn = findViewById<Button>(R.id.addRecipeButton)
        val addIngredientBtn = findViewById<Button>(R.id.addIngredientButton)


        cancelBtn.setOnClickListener{
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
        addBtn.setOnClickListener {
            when {
                recipeName.text.isEmpty() -> {
                    Toast.makeText(this, "Anna reseptille nimi", Toast.LENGTH_SHORT).show()
                }
                index == 1 -> {
                    Toast.makeText(this, "Reseptissä ei ole ainesosia", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    /*
                            Nämä tallennetaan sql tässä
                            recipeName.text.toString())
                            ingredients
                            */
                    val intent = Intent(this, RecipeActivity::class.java)
                    intent.putExtra("Name", recipeName.text.toString())
                    intent.putStringArrayListExtra("Ingredients", ingredients)
                    Toast.makeText(this, "Resepti tallennettu", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        }

        addIngredientBtn.setOnClickListener {
            addIngredient(addIngredientText.text.toString(), textLayout)
            addIngredientText.text.clear()
        }

    }


    fun addIngredient(ingredient: String, view: LinearLayout){

        val newIngredient : TextView = TextView(this)
        newIngredient.id = index
        newIngredient.text = "$index. $ingredient"
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)

        params.setMargins(30, 30, 15, 30)
        newIngredient.layoutParams = params

        view.addView(newIngredient)
        ingredients.add(ingredient)

        //if success index += 1
        index += 1

    }
}




