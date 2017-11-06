package me.rolgalan.pokemon.backpack

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.pokemon_list_item.view.*
import me.rolgalan.pokemon.R
import me.rolgalan.pokemon.model.Pokemon

/**
 * Created by Roldán Galán on 06/11/2017.
 */
class PokemonRecyclerViewAdapter(private val mValues: List<Pokemon>) :
        RecyclerView.Adapter<PokemonRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Pokemon
            v.context.startActivity(PokemonDetailActivity.getIntent(item, v.context))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.pokemon_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.setPokemon(item)
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        private val name: TextView = mView.item_name
        private val image: ImageView = mView.item_image

        fun setPokemon(pokemon: Pokemon) {
            itemView.tag = pokemon
            itemView.setOnClickListener(onClickListener)
            loadFields(pokemon)
        }

        private fun loadFields(pokemon: Pokemon) {
            name.text = pokemon.name
            loadImage(pokemon.getFrontImage())
        }

        private fun loadImage(url: String?) {
            if (url != null) {
                loadValidImage(url)
            } else {
                loadPlaceholder()
            }
        }

        private fun loadPlaceholder() {

            Glide.with(image.context)
                    .load(R.drawable.placeholder)
                    .fitCenter()
                    .into(image)
        }

        private fun loadValidImage(url: String) {
            Glide.with(image.context)
                    .load(url)
                    .fitCenter()
                    .placeholder(R.drawable.placeholder)
                    .crossFade()
                    .into(image)
        }
    }
}