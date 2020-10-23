package com.example.recipefinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchButton.setOnClickListener {
            var intent = Intent(this, RecipeList::class.java)
            var ingredients = IngredientsEdt.text.toString().trim()
            var searchTerm = searchTermEdt.text.toString().trim()

            intent.putExtra("ingredients", ingredients)
            intent.putExtra("search", searchTerm)


            startActivity(intent)
    }
}}