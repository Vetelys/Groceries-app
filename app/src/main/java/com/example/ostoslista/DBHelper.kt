package com.example.ostoslista
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VER) {

    companion object{
        //singleton class to prevent multiple instances
        private var INSTANCE: DBHelper? = null

        fun getInstance(context: Context): DBHelper{
            if(INSTANCE == null){
                INSTANCE = DBHelper(context)
            }
            return INSTANCE!!
        }

        private const val DB_VER = 1
        private const val DB_NAME = "recipes.db"

        private const val TBL_INGREDIENTS = "ingredients"
        private const val TBL_RECIPES = "recipes"
        private const val TBL_SHOPPINGLIST = "shopping_list"

        private const val ID = "id"
        private const val RECIPE_ID = "recipe_id"
        private const val NAME = "name"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblIngredient = ("CREATE TABLE $TBL_INGREDIENTS ($ID INTEGER PRIMARY KEY, $NAME TEXT, $RECIPE_ID INTEGER)")
        val createTblRecipe = ("CREATE TABLE $TBL_RECIPES ($ID INTEGER PRIMARY KEY, $NAME TEXT)")
        val createTblShoppingList = ("CREATE TABLE $TBL_SHOPPINGLIST ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $NAME TEXT)")
        db?.execSQL(createTblIngredient)
        db?.execSQL(createTblRecipe)
        db?.execSQL(createTblShoppingList)
    }

    override fun onUpgrade(db: SQLiteDatabase?, old: Int, new: Int) {
        db!!.execSQL("""DROP TABLE IF EXISTS $TBL_INGREDIENTS""")
        db.execSQL("""DROP TABLE IF EXISTS $TBL_RECIPES""")
        db.execSQL("""DROP TABLE IF EXISTS $TBL_SHOPPINGLIST""")
        onCreate(db)
    }

    fun insertProduct(product: String): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(NAME, product)

        val success = db.insert(TBL_SHOPPINGLIST, null, contentValues)
        db.close()
        return success
    }

    fun removeProduct(product: String): Int{
        val db = this.writableDatabase

        val success = db.delete(TBL_SHOPPINGLIST, "$NAME=?", arrayOf(product))
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getAllProducts(): ArrayList<String> {
        val productList: ArrayList<String> = ArrayList()
        val query = "SELECT $NAME FROM $TBL_SHOPPINGLIST"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(query, null)
        }catch(e: Exception){
            e.printStackTrace()
            db.execSQL(query)
            return ArrayList()
        }
        var productName: String
        if(cursor.moveToFirst()){
            do{
                productName = cursor.getString(cursor.getColumnIndex(NAME))
                productList.add(productName)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return productList
    }

    fun addRecipe(recipe_name: String, ingredients: ArrayList<String>) : Long{
        val db = this.writableDatabase
        val recipeIdQuery = "SELECT COALESCE(MAX($ID), 0) AS $ID FROM $TBL_RECIPES"
        val ingredientIdQuery = "SELECT COALESCE(MAX($ID), 0) AS $ID FROM $TBL_INGREDIENTS"

        val recipeQuery = db.rawQuery(recipeIdQuery, null)
        var success: Long = -1

        if(recipeQuery.moveToFirst()) {
            val recipeId = recipeQuery.getInt(0);


            val recipeContentValues = ContentValues()
            recipeContentValues.put(NAME, recipe_name)
            recipeContentValues.put(ID, recipeId + 1)

            success = db.insert(TBL_RECIPES, null, recipeContentValues)

            if(success != -1L) {
                for (ingredient in ingredients) {
                    val ingredientQuery = db.rawQuery(ingredientIdQuery, null)

                    if(ingredientQuery.moveToFirst()){

                        val ingredientId = ingredientQuery.getInt(0)

                        val contentValues = ContentValues()
                        contentValues.put(NAME, ingredient)
                        contentValues.put(ID, ingredientId + 1)
                        contentValues.put(RECIPE_ID, recipeId + 1)

                        success = db.insert(TBL_INGREDIENTS, null, contentValues)

                    }
                }
            }
        }

        db.close()
        return success
    }

    fun removeRecipe(recipeName: String) {
        val db = this.writableDatabase
        val recipeQuery = """SELECT $ID FROM $TBL_RECIPES WHERE $NAME = "$recipeName""""
        val queryResult = db.rawQuery(recipeQuery, null)

        if(queryResult.moveToFirst()) {
            val recipeId = queryResult.getInt(0)

            db.delete(TBL_RECIPES, "$ID=$recipeId", null)
            db.delete(TBL_INGREDIENTS, "$RECIPE_ID=$recipeId", null)
            db.close()

        }
    }

    @SuppressLint("Range")
    fun getRecipes() : ArrayList<String> {
        val recipeList: ArrayList<String> = ArrayList()
        val query = "SELECT $NAME FROM $TBL_RECIPES"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(query, null)
        }catch(e: Exception){
            e.printStackTrace()
            db.execSQL(query)
            return ArrayList()
        }
        var recipeName: String
        if(cursor.moveToFirst()){
            do{
                recipeName = cursor.getString(cursor.getColumnIndex(NAME))
                recipeList.add(recipeName)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return recipeList
    }

    @SuppressLint("Range")
    fun getAllIngredients(recipe_name: String): ArrayList<String> {
        val ingredientList: ArrayList<String> = ArrayList()
        val query = """SELECT $NAME FROM $TBL_INGREDIENTS WHERE $RECIPE_ID = (SELECT $ID FROM $TBL_RECIPES WHERE $NAME = "$recipe_name")"""
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(query, null)
        }catch(e: Exception){
            e.printStackTrace()
            db.execSQL(query)
            return ArrayList()
        }
        var ingredientName: String
        if(cursor.moveToFirst()){
            do{
                ingredientName = cursor.getString(cursor.getColumnIndex(NAME))
                ingredientList.add(ingredientName)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return ingredientList
    }
}