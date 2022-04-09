package fr.isen.dubost.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
        binding.detailTotal.text = item.prices[0].price + " €"
        //binding.detailTitle.text = intent.getStringExtra(CategoryActivity.ITEM_KEY)

        val carouselAdapter = CarouselAdapter(this, item.images)
        binding.detailSlider.adapter = carouselAdapter

        binding.detailIngredient.text = item.ingredients.joinToString { it.name_fr }


        var cnt = 1
        binding.detailQuantity.text = cnt.toString()
        binding.buttonMoins.setOnClickListener {
            cnt--
            if(cnt <= 1)
                cnt = 1
            binding.detailQuantity.text = cnt.toString()
            binding.detailTotal.text = (item.prices[0].price.toInt()*cnt).toString()+ " €"
        }
         binding.buttonPlus.setOnClickListener {
             cnt++
             binding.detailQuantity.text = cnt.toString()
             binding.detailTotal.text = (item.prices[0].price.toInt()*cnt).toString()+ " €"
        }

        binding.buttonAjouter.setOnClickListener {

        }




        var actionBar = supportActionBar
        actionBar!!.title = item.name_fr
        //actionBar.setDisplayHomeAsUpEnabled(true)
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
}