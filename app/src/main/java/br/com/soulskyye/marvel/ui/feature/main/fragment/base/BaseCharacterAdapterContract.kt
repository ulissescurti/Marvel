package br.com.soulskyye.marvel.ui.feature.main.fragment.base

import br.com.soulskyye.marvel.data.model.Character

interface BaseCharacterAdapterContract {

    interface Presenter {

        fun onFavoriteClick(character: Character)
    }
}