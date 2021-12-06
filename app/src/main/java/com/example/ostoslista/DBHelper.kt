package com.example.ostoslista
import android.content.ContentValues
import android.content.Context
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
        private const val INGREDIENT_ID = "ingredient_id"
        private const val RECIPE_ID = "recipe_id"
        private const val NAME = "name"
        private const val AMOUNT = "amount"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblIngredient = ("CREATE TABLE $TBL_INGREDIENTS ($ID INTEGER PRIMARY KEY, $NAME TEXT, $AMOUNT TEXT, $RECIPE_ID INTEGER FOREIGN KEY)")
        val createTblRecipe = ("CREATE TABLE $TBL_RECIPES ($ID INTEGER PRIMARY KEY, $NAME TEXT, $INGREDIENT_ID INTEGER FOREIGN KEY)")
        val createTblShoppingList = ("CREATE TABLE $TBL_SHOPPINGLIST ($ID INTEGER PRIMARY KEY, $NAME TEXT)")
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
    /*
    fun insertIngredient(ing: Ingredient): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()

        val success = db.insert(TBL_INGREDIENTS, null, contentValues)
        db.close()
        return success

    }
    fun insertRecipe(recipe: Recipe): Long{

    }
    fun insertProduct(prod: Product): Long{

    }
     */
}