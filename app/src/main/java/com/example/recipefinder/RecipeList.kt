package com.example.recipefinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_recipe_list.*
import org.json.JSONException
import org.json.JSONObject

class RecipeList : AppCompatActivity() {

    var urlString = "http://www.recipepuppy.com/api/?i=onions,garlic&q=omelet&p=3"

    var url: String? = null



    var volleyRequest: RequestQueue? = null
    var recipeList: ArrayList<Recipe>? = null
    var recipeAdapter: RecipeListAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        var extras = intent.extras
        var ingredients = extras?.get("ingredients")
        var searchTerm = extras?.get("search")

        if (extras != null && !ingredients?.equals("")!!
            && !searchTerm?.equals("")!!
        )
        {
            //construct our url
            var tempUrl = LEFT_LINK + ingredients + QUERY + searchTerm
            urlString = tempUrl

        }

        else
        {

            urlString

        }

        recipeList = ArrayList()

        volleyRequest = Volley.newRequestQueue(this)


        getRecipe(urlString)

    }







    fun getRecipe(url: String) {


        val recipeRequest = JsonObjectRequest(
            Request.Method.GET,
            url, null,

            Response.Listener { response: JSONObject ->
                try {

                    val resultArray = response.getJSONArray("results")

                    for (i in 0..resultArray.length() - 1) {
                        var recipeObj = resultArray.getJSONObject(i)


                        var title = recipeObj.getString("title")
                        var link = recipeObj.getString("href")
                        var thumbnail = recipeObj.getString("thumbnail")
                        var ingredients = recipeObj.getString("ingredients")

                        Log.d("Result==>>", title)

                        var recipe = Recipe()
                        recipe.title = title
                        recipe.link = link
                        recipe.thumbnail = thumbnail
                        recipe.ingredients = "Ingredients: ${ingredients}"


                        recipeList!!.add(recipe)

                        recipeAdapter = RecipeListAdapter(recipeList!!, this)
                        layoutManager = LinearLayoutManager(this)

                        //setup list/recyclerview
                        recyclerViewId.layoutManager = layoutManager
                        recyclerViewId.adapter = recipeAdapter


                    }

                    recipeAdapter!!.notifyDataSetChanged()


                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            },
            Response.ErrorListener { error: VolleyError? ->
                try {
                    Log.d("Error:", error.toString())

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

        )


        volleyRequest!!.add(recipeRequest)

        return
    }
}