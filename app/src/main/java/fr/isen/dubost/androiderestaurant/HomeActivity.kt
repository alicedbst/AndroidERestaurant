package fr.isen.dubost.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.StringRes
import fr.isen.dubost.androiderestaurant.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.entree.setOnClickListener {
            goToCategory(getString(R.string.entrée))
        }
        binding.plat.setOnClickListener {
            goToCategory(getString(R.string.plat))
        }
        binding.dessert.setOnClickListener {
            goToCategory(getString(R.string.dessert))
        }


    }
    private fun goToCategory(category: String) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("HomeActivity", "Leave the home page")
    }


}