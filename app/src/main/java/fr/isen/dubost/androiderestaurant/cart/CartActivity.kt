package fr.isen.dubost.androiderestaurant.cart

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import fr.isen.dubost.androiderestaurant.DetailActivity
import fr.isen.dubost.androiderestaurant.HomeActivity
import fr.isen.dubost.androiderestaurant.R
import fr.isen.dubost.androiderestaurant.databinding.ActivityCartBinding
import java.io.File

class CartActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCartBinding
    private val itemsList = ArrayList<LinesCart>()
    private lateinit var cartAdapter: CartAdapter
    private lateinit var cart: Cart



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        cart = readCart()

        title = "Panier"

        //setup du recycler view
        val recyclerPanier: RecyclerView = binding.cartRecyclerView
        cartAdapter = CartAdapter(itemsList, CartAdapter.OnClickListener { item ->
            onListCartClickDelete(item)
        },this@CartActivity)

        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerPanier.layoutManager = layoutManager
        recyclerPanier.adapter = cartAdapter
        cart.lines.forEach { ligne: LinesCart -> itemsList.add(ligne) }
        cartAdapter.notifyDataSetChanged()


        updateTotalPrice()

        //boutton passer commande
        binding.button.setOnClickListener {
            var cmd ="Voici le contenu de ma commande :\n"
            cart.lines.forEach { ligne:LinesCart -> cmd+="${ligne.Item.name_fr} * ${ligne.quantite} \n" }
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun updateTotalPrice(){
        var totalPrice = 0.0F
        this.cart.lines.forEach { ligne:LinesCart -> totalPrice+=ligne.Item.prices[0].price.toFloat()*ligne.quantite }
        binding.totalPrice.text=getString(R.string.euro,totalPrice.toString())
        //binding.totalPrice.text= (totalPrice).toString()+ " €"

    }

    private fun onListCartClickDelete(item: LinesCart) {
        Toast.makeText(this@CartActivity, "${item.quantite} ${item.Item.name_fr} enlevé(s) du panier",Toast.LENGTH_SHORT).show()
        this.itemsList.remove(item)
        this.cart.lines.remove(item)
        Log.d("PANIER",cart.lines.size.toString())
        this.writeCart()
        this.updateTotalPrice()
        this.cartAdapter.notifyDataSetChanged()
    }

    private fun writeCart(){
        //sauvegarde du panier en json dans les fichiers
        val cartJson = Gson().toJson(cart)
        val filename = "panier.json"
        this.binding.root.context.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(cartJson.toByteArray())
        }
    }

    private fun readCart(): Cart {
        //lecture fichier panier
        val filename = "panier.json"
        //val file = File(binding.root.context.filesDir, filename)
        val file = File(cacheDir.absolutePath + DetailActivity.BASKET_FILE)
        if(file.exists()){
            val contents = file.readText()
            return Gson().fromJson(contents, Cart::class.java)
        }else{
            return Cart(ArrayList())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }

}