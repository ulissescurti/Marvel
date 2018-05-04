package br.com.soulskyye.marvelheroes.ui.detail

import br.com.soulskyye.marvel.data.DataManager
import br.com.soulskyye.marvel.data.model.Character
import br.com.soulskyye.marvel.data.network.RetrofitException
import br.com.soulskyye.marvel.data.network.model.CharactersResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class CharacterDetailActivityPresenter(private var view: CharacterDetailActivityContract.View?,
                                       private var character: Character,
                                       private var isFromFavorite: Boolean,
                                       private var dataManager: DataManager) : CharacterDetailActivityContract.Presenter {

    private val compositeDisposable by lazy { CompositeDisposable() }

    /*
        Presenter Contract
     */
    override fun start() {
        view?.showImage("${character.thumbnail?.path}.${character.thumbnail?.extension}")

        if(isFromFavorite){
            view?.hideLoading()
            showDetails()
        } else {
            requestCharacter()
        }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
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

    private fun requestCharacter(){
        view?.showLoading()

        val disposable = dataManager.getCharacter(character.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onFetchCharacterSuccess, this::onFetchCharactersError)

        compositeDisposable.add(disposable)
    }

    override fun onTryAgainClick() {
        view?.hideTryAgain()
        requestCharacter()
    }

    private fun onFetchCharacterSuccess(response: CharactersResponse){
        view?.hideLoading()

        if(response.data?.results?.isNotEmpty()!!) {
            val isFavorite = character.isFavorite
            character = response.data?.results!![0]
            character.isFavorite = isFavorite
            showDetails()
        }
    }

    private fun onFetchCharactersError(error: Throwable){
        val exception = dataManager.asRetrofitException(error)

        if (exception.kind == RetrofitException.Kind.NETWORK && exception.message != "timeout") {
            view?.showInternetError()
        } else {
            view?.showGenericError()
        }

        view?.hideLoading()
    }

    private fun showDetails(){
        updateFavoriteMenu()
        view?.showName(character.name)
        view?.showDescription(character.description)
        if(character.comics?.items?.isNotEmpty()!!){
            view?.showComics(character.comics?.items!!)
        } else {
            view?.hideComics()
        }
        if(character.series?.items?.isNotEmpty()!!){
            view?.showSeries(character.series?.items!!)
        } else {
            view?.hideSeries()
        }
    }

}