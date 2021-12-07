package com.example.tools

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


fun hideKeyboard(edittext: EditText) {
    val context = edittext.context
val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
imm.hideSoftInputFromWindow(edittext.windowToken, 0)
}

 fun showKeyBoard(view: View) {
     val context = view.context
     val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
     imm.showSoftInput(view, 0)
 }

