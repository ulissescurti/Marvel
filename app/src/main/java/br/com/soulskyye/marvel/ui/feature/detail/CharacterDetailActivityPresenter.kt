package br.com.soulskyye.marvelheroes.ui.detail

import br.com.soulskyye.marvel.data.DataManager
import br.com.soulskyye.marvel.data.model.Character


class CharacterDetailActivityPresenter(private var view: CharacterDetailActivityContract.View?,
                                       private var character: Character,
                                       private var dataManager: DataManager) : CharacterDetailActivityContract.Presenter {

    /*
        Presenter Contract
     */
    override fun start() {
        view?.showImage(character.thumbnail)
        view?.showName(character.name)
        view?.showDescription(character.description)

    }

    override fun onDestroy() {
        view = null
    }

    override fun updateFavoriteMenu() {
        if(character.isFavorite){
            view?.showFavoriteIcon()
        } else {
            view?.showFavoriteEmptyIcon()
        }
    }

    override fun onFavoriteClick() {
        if(character.isFavorite) {
            character.isFavorite = false
            dataManager.deleteFavorite(character)
            view?.showFavoriteEmptyIcon()
        } else {
            character.isFavorite = true
            dataManager.insertFavorite(character)
            view?.showFavoriteIcon()
        }
    }
}