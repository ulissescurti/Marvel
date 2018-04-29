package br.com.soulskyye.marvel.ui.main.fragment.character.adapter

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.soulskyye.marvel.R
import br.com.soulskyye.marvel.data.model.Character
import br.com.soulskyye.marvel.ui.main.fragment.character.CharacterListFragmentContract
import br.com.soulskyye.marvel.utils.loadImage
import br.com.soulskyye.marvel.utils.setPaletteColor

class CharacterListAdapter(private var list: ArrayList<Character>,
                           private var context: Context?,
                           private var presenter: CharacterListFragmentContract.Presenter,
                           private var screenWidth: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_character, parent, false)
        val imageViewCharacter: AppCompatImageView = v.findViewById(R.id.imageViewCharacter)
        imageViewCharacter.layoutParams.width = screenWidth/2
        imageViewCharacter.layoutParams.height = screenWidth/2
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val character = list[position]
        val viewHolder = holder as ViewHolder

        viewHolder.imageViewCharacter.loadImage("${character.thumbnail?.path}.${character.thumbnail?.extension}",
                {
                    viewHolder.textViewName.setPaletteColor(it)
                })
        viewHolder.textViewName.text = character.name

        viewHolder.view.setOnClickListener {
            //            presenter.onListItemClick(position, it.findViewById(R.id.imageViewCharacter))
        }
    }

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var textViewName: TextView = view.findViewById(R.id.textViewCharacterName)
        var imageViewCharacter: AppCompatImageView = view.findViewById(R.id.imageViewCharacter)
    }

    fun addItems(newList: ArrayList<Character>) {
        list.addAll(newList)
        notifyItemRangeInserted(list.size - newList.size, list.size - 1)
    }


}