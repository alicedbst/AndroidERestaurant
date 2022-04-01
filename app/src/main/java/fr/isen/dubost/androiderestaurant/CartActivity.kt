package fr.isen.dubost.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.dubost.androiderestaurant.databinding.ActivityCartBinding
import fr.isen.dubost.androiderestaurant.databinding.ActivityCategoryBinding

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cartList.layoutManager = LinearLayoutManager(this)
        binding.cartList.adapter = CategoryAdapter(arrayListOf()) {

        }

        var actionBar = supportActionBar
        actionBar!!.title = "Panier"

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }
}