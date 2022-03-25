package fr.isen.dubost.androiderestaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.dubost.androiderestaurant.model.Item


class CategoryAdapter(val data: ArrayList<Item>, val clickListener:(Item) -> Unit ) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemTitle: TextView = view.findViewById(R.id.categoryTitle)
        var itemPrice: TextView = view.findViewById(R.id.categoryPrice)
        var itemImage: ImageView = view.findViewById(R.id.itemImage)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = data[position]
        holder.itemTitle.text = item.name_fr
        holder.itemPrice.text = item.prices[0].price +" €"
        //holder.itemImage.text = item.name_fr
        Picasso.get().load(item.images[0].ifEmpty { null })
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.itemImage)
        holder.itemView.setOnClickListener {
            clickListener(item)
        }
    }

//affiche le nb d'éléments du RecyclerView
    override fun getItemCount(): Int {
        return data.size
    }
}
