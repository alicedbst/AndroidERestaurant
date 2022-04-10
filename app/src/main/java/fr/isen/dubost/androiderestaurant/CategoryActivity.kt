package fr.isen.dubost.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.dubost.androiderestaurant.cart.CartActivity
import fr.isen.dubost.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.dubost.androiderestaurant.model.DataResult
import org.json.JSONObject

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryTitle = intent.getStringExtra("category") ?: ""



        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = CategoryAdapter(arrayListOf()) {

        }

        loadDataFromServerByCategory(categoryTitle)

        var actionBar = supportActionBar
        actionBar!!.title = categoryTitle
        //actionBar.setDisplayHomeAsUpEnabled(true)


    }

    companion object {
        val ITEM_KEY = "item"
    }

    private fun loadDataFromServerByCategory(category: String) {
        //val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val jsonData = JSONObject()
        jsonData.put("id_shop", "1")

        val request = JsonObjectRequest(Request.Method.POST, url, jsonData,
            { response ->
                val strResp = response.toString()
                val dataResult = Gson().fromJson(strResp, DataResult::class.java)

                val items =
                    dataResult.data.firstOrNull { it.name_fr == category }?.items ?: arrayListOf()
                Log.d("CategoryActivity", dataResult.toString())
                binding.recyclerView.adapter = CategoryAdapter(items) {
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra(ITEM_KEY, it)
                    startActivity(intent)
                }
            }, {
                Log.d("CategoryActivity", "Volley error: $it")

            })
        Volley.newRequestQueue(this).add(request)
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