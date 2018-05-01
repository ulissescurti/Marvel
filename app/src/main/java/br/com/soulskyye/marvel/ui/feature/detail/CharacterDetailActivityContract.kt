package br.com.soulskyye.marvelheroes.ui.detail

import br.com.soulskyye.marvel.data.model.Thumbnail
import br.com.soulskyye.marvel.ui.base.BaseUiContract

interface CharacterDetailActivityContract {

    interface Presenter : BaseUiContract.Presenter {
        fun onFavoriteClick()
        fun updateFavoriteMenu()

    }

    interface View : BaseUiContract.View {

        fun showImage(thumbnail: Thumbnail?)

        fun showName(name: String)

        fun showDescription(description: String)
        fun showFavoriteIcon()
        fun showFavoriteEmptyIcon()

    }

}