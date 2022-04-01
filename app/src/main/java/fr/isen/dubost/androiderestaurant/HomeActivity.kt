package fr.isen.dubost.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.annotation.StringRes
import fr.isen.dubost.androiderestaurant.ble.BleScanActivity
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
        binding.bluetooth.setOnClickListener {
            val intent = Intent(this, BleScanActivity::class.java)
            startActivity(intent)
        }

        var actionBar = supportActionBar
        actionBar!!.title = "Home"

    }

    private fun goToCategory(category: String) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("HomeActivity", "Leave the home page")
    }


}