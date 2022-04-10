package fr.isen.dubost.androiderestaurant.cart

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.dubost.androiderestaurant.R
import fr.isen.dubost.androiderestaurant.databinding.ItemCartBinding


class CartAdapter(
    private var itemsList: MutableList<LinesCart>,
    private val onClickListener:OnClickListener,
    private val context: Context

    ): RecyclerView.Adapter<CartAdapter.MyViewHolder>(){

    private lateinit var binding: ItemCartBinding

    inner class MyViewHolder(binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        val name=binding.nameItem
        val quantity=binding.quantity
        val totalItemPrice=binding.price
        val btnDelete = binding.delete
        val com = binding.commentaireText

    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        holder.name.text =item.Item.name_fr
        holder.quantity.text=item.quantite.toString()
        holder.totalItemPrice.text= "€"
        holder.btnDelete.setOnClickListener {
            onClickListener.onClick(item)
        }
        //holder.com.text=context.getString(R.string.com, item.com)
//,item.com

    }
    override fun getItemCount(): Int {
        return itemsList.size
    }

    class OnClickListener(val clickListener: (item: LinesCart) -> Unit) {
        fun onClick(item: LinesCart) = clickListener(item)
    }

}
