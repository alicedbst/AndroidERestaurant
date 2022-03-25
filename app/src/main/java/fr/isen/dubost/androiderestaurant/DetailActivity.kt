package fr.isen.dubost.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import fr.isen.dubost.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.dubost.androiderestaurant.model.Item

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    lateinit var item: Item
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        item = intent.getSerializableExtra(CategoryActivity.ITEM_KEY) as Item
        binding.detailTitle.text = item.name_fr
        //binding.detailTitle.text = intent.getStringExtra(CategoryActivity.ITEM_KEY)

        val carouselAdapter = CarouselAdapter(this, item.images)
        binding.detailSlider.adapter = carouselAdapter

        binding.detailIngredient.text = item.ingredients.joinToString { it.name_fr }

        /*var moinscnt = 0
        setContentView(R.layout.activity_detail)
        binding.buttonMoins.setOnClickListener {
            moinscnt--
        }
        var pluscnt = 0
         binding.buttonPlus.setOnClickListener {
             pluscnt++
        }*/


        var actionBar = supportActionBar
        actionBar!!.title = item.name_fr
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
}