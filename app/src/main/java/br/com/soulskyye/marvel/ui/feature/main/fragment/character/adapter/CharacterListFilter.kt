package br.com.soulskyye.marvel.ui.feature.main.fragment.character.adapter

import android.widget.Filter
import br.com.soulskyye.marvel.data.model.Character

class CharacterListFilter(var filterList: ArrayList<Character>,
                          var adapter: CharacterListAdapter) : Filter() {

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        val results = Filter.FilterResults()

        val query = constraint?.toString()?.toUpperCase()

        if (query != null && query.isNotEmpty()) {
            val filteredList = ArrayList<Character>()

            for (i in 0 until filterList.size) {
                if (filterList[i].name.toUpperCase().contains(query)) {
                    filteredList.add(filterList[i])
                }
            }

            results.count = filteredList.size
            results.values = filteredList
        } else {
            results.count = filterList.size
            results.values = filterList
        }

        return results
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        adapter.list = results?.values as ArrayList<Character>

        adapter.notifyDataSetChanged()
    }

}