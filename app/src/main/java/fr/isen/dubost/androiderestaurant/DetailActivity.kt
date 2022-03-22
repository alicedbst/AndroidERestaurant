package fr.isen.dubost.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.dubost.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.dubost.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.dubost.androiderestaurant.databinding.ActivityHomeBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.detailTitle.text = intent.getStringExtra(CategoryActivity.ITEM_KEY)
    }
}