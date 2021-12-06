package com.example.ostoslista


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.DialogFragment

//https://www.youtube.com/watch?v=SkFcDWt9GV4

class AddProductDialogFragment : DialogFragment() {

    private var index: Int = 1
    val ingredients = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_add_product_dialog, container, false)
        val recipeName = rootView.findViewById<EditText>(R.id.recipeName)
        recipeName.requestFocus()
        val textLayout = rootView.findViewById<LinearLayout>(R.id.recipeIngredientLayout)
        val addIngredientText = rootView.findViewById<EditText>(R.id.addIngredientEditText)
        val cancelBtn = rootView.findViewById<Button>(R.id.cancelRecipeButton)
        val addBtn = rootView.findViewById<Button>(R.id.addRecipeButton)
        val addIngredientBtn = rootView.findViewById<Button>(R.id.addIngredientButton)

        cancelBtn.setOnClickListener{
            dismiss()
        }
        addBtn.setOnClickListener {
            if(recipeName.text.isEmpty()){
                Toast.makeText(activity, "Anna reseptille nimi", Toast.LENGTH_SHORT).show()
            }
            else if(index == 1){
                Toast.makeText(activity, "Reseptissä ei ole ainesosia", Toast.LENGTH_SHORT).show()
            }else{
                /*
                Nämä tallennetaan sql tässä
                recipeName.text.toString())
                ingredients
                */
                Toast.makeText(activity, "Resepti tallennettu", Toast.LENGTH_SHORT).show()
                dismiss()
            }

        }

        addIngredientBtn.setOnClickListener {
            addIngredient(addIngredientText.text.toString(), textLayout)
            addIngredientText.text.clear()
        }

        return rootView
    }


    fun addIngredient(ingredient: String, view: LinearLayout){

        val newIngredient : TextView = TextView(activity)
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


fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}


