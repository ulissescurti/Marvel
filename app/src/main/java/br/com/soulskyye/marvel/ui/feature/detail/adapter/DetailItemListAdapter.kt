package br.com.soulskyye.marvel.ui.feature.detail.adapter

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import br.com.soulskyye.marvel.R
import br.com.soulskyye.marvel.data.model.Character
import br.com.soulskyye.marvel.data.model.Item
import br.com.soulskyye.marvel.ui.feature.main.fragment.base.BaseCharacterContract
import br.com.soulskyye.marvel.utils.loadImage
import br.com.soulskyye.marvel.utils.setPaletteColor

class DetailItemListAdapter(var list: ArrayList<Item>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_detail_items, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        val viewHolder = holder as ViewHolder

        viewHolder.textView.text = item.name
        viewHolder.imageView.loadImage(item.image, R.drawable.placeholder_marvel, {})
    }


    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView = view.findViewById(R.id.textViewDetailItem)
        var imageView: AppCompatImageView = view.findViewById(R.id.imageViewDetailItem)
    }


}