package br.com.soulskyye.marvelheroes.ui.detail

import br.com.soulskyye.marvel.data.DataManager
import br.com.soulskyye.marvel.data.model.Character
import br.com.soulskyye.marvel.data.network.RetrofitException
import br.com.soulskyye.marvel.data.network.model.CharactersResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class CharacterDetailActivityPresenter(private var view: CharacterDetailActivityContract.View?,
                                       private var offlineCharacter: Character?,
                                       private var characterId: String?,
                                       private var characterImage: String,
                                       private var dataManager: DataManager) : CharacterDetailActivityContract.Presenter {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private var character: Character? = null

    /*
        Presenter Contract
     */
    override fun start() {
        view?.showImage(characterImage)
        if(characterId != null){
            requestCharacter()
        } else {
            character = offlineCharacter
            view?.hideLoading()
            showDetails()
        }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        view = null
    }

    override fun updateFavoriteMenu() {
        if(character != null && character!!.isFavorite){
            view?.showFavoriteIcon()
        } else {
            view?.showFavoriteEmptyIcon()
        }
    }

    override fun onFavoriteClick() {
        if(character?.isFavorite!!) {
            character?.isFavorite = false
            dataManager.deleteFavorite(character!!)
            view?.showFavoriteEmptyIcon()
        } else {
            character?.isFavorite = true
            dataManager.insertFavorite(character!!)
            view?.showFavoriteIcon()
        }
    }

    private fun requestCharacter(){
        view?.showLoading()

        val disposable = dataManager.getCharacter(characterId!!)
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
            character = response.data?.results!![0]
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
        view?.showName(character?.name!!)
        view?.showDescription(character?.description!!)
        if(character?.comics?.items?.isNotEmpty()!!){
            view?.showComics(character?.comics?.items!!)
        } else {
            view?.hideComics()
        }
        if(character?.series?.items?.isNotEmpty()!!){
            view?.showSeries(character?.series?.items!!)
        } else {
            view?.hideSeries()
        }
    }

}