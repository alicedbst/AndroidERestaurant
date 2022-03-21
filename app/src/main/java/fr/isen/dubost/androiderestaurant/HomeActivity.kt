package fr.isen.dubost.androiderestaurant

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // val button = findviewbyid
        //button.setonclicklisterner{`
        //startactivit
        val textViewEntree = findViewById<Button>(R.id.buttonEntree)
        textViewEntree.setOnClickListener {
            val intent = Intent(this, CategorieActivity::class.java)
            startActivity(intent)
        }
        val textViewPlat = findViewById<Button>(R.id.buttonPlat)
        textViewPlat.setOnClickListener {
            val intent = Intent(this, CategorieActivity::class.java)
            startActivity(intent)
        }
        val textViewDessert = findViewById<Button>(R.id.buttonDessert)
        textViewDessert.setOnClickListener {
            val intent = Intent(this, CategorieActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("HomeActivity", "Home destroy")
    }

}