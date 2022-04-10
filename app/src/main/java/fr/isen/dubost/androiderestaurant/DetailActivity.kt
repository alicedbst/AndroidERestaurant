package fr.isen.dubost.androiderestaurant

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.dubost.androiderestaurant.cart.Cart
import fr.isen.dubost.androiderestaurant.cart.CartActivity
import fr.isen.dubost.androiderestaurant.cart.LinesCart
import fr.isen.dubost.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.dubost.androiderestaurant.model.Item
import java.io.File
import java.net.URI.create

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    lateinit var item: Item
    private lateinit var cart: Cart
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)


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


        val plat = intent.getSerializableExtra("item") as Item
        binding.detailTitle.text = plat.name_fr

        binding.buttonAjouter.setOnClickListener{
            Snackbar.make(binding.root, "Votre plat a été ajouté au panier.", Snackbar.LENGTH_LONG).show()
            saveInBasket(cnt, plat)

        }

        var actionBar = supportActionBar
        actionBar!!.title = item.name_fr
    }


    private fun saveInBasket(quantity: Int, item: Item) {
        val jsonFile = File(cacheDir.absolutePath + BASKET_FILE)

        if (jsonFile.exists()) {
            val dataJson = jsonFile.readText()
            if (dataJson.isNotEmpty()) {
                val basket = GsonBuilder().create().fromJson(dataJson, Cart::class.java)
                updateOrCreateBasket(basket, quantity, item)
            } else {
                updateOrCreateBasket(null, quantity, item)
            }
        } else {
            updateOrCreateBasket(null, quantity, item)
        }
    }

    private fun updateOrCreateBasket(cart: Cart?, quantity: Int, item: Item) {
        val newCart = cart ?: Cart(mutableListOf())
        newCart.lines.add(LinesCart(quantity, item))

        saveDishCountInPref(newCart)

        File(cacheDir.absolutePath + BASKET_FILE).writeText(
            GsonBuilder().create().toJson(newCart)
        )
        invalidateOptionsMenu()
    }

    private fun saveDishCountInPref(cart: Cart) {
        val count = cart.lines.sumOf { it.quantite }
        val editor = sharedPreferences.edit()
        editor.putInt(BASKET_COUNT, count)
        editor.apply()
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

    companion object {
        const val APP_PREFS = "app_prefs"
        const val BASKET_FILE = "basket.json"
        const val BASKET_COUNT = "basket_count"
    }
}