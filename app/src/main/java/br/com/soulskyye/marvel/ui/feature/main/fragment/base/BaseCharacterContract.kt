package br.com.soulskyye.marvel.ui.feature.main.fragment.base

import br.com.soulskyye.marvel.data.model.Character

interface BaseCharacterContract {

    interface Presenter {

        fun onListItemClick(character: Character?, characterId: String?, characterImage: String, imageView: android.view.View)
        fun onFavoriteClick(character: Character)

        fun refresh()
    }

    interface View {

        fun startDetailActivity(character: Character?, characterId: String?, characterImage: String, imageView: android.view.View)
    }
}