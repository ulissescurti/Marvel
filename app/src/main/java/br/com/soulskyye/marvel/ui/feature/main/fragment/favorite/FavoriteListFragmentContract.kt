package br.com.soulskyye.marvel.ui.feature.main.fragment.favorite

import br.com.soulskyye.marvel.data.model.Character
import br.com.soulskyye.marvel.ui.base.BaseUiContract
import br.com.soulskyye.marvel.ui.feature.main.fragment.base.BaseCharacterContract

interface FavoriteListFragmentContract {

    interface View : BaseUiContract.View, BaseCharacterContract.View {
        fun showFavorites(characterList: List<Character>)

    }

    interface Presenter : BaseUiContract.Presenter, BaseCharacterContract.Presenter {

    }

}