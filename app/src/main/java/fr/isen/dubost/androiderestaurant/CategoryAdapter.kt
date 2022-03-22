package fr.isen.dubost.androiderestaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.dubost.androiderestaurant.model.Item


class CategoryAdapter(val data: ArrayList<Item>, val clickListener:(Item) -> Unit ) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemTitle: TextView = view.findViewById(R.id.categoryTitle)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = data[position]
        holder.itemTitle.text = item.name_fr
        holder.itemView.setOnClickListener {
            clickListener(item)
        }
    }

//affiche le nb d'éléments du RecyclerView
    override fun getItemCount(): Int {
        return data.size
    }
}
