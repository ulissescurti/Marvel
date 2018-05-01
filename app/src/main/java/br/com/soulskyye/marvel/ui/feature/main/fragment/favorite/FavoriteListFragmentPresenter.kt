package br.com.soulskyye.marvel.ui.feature.main.fragment.favorite

import android.view.View
import br.com.soulskyye.marvel.data.DataManager
import br.com.soulskyye.marvel.data.model.Character
import br.com.soulskyye.marvel.data.network.model.CharactersResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FavoriteListFragmentPresenter(private var view: FavoriteListFragmentContract.View?,
                                    private var dataManager: DataManager) : FavoriteListFragmentContract.Presenter {


    private val compositeDisposable by lazy {
        CompositeDisposable()
    }


    override fun start() {
        loadAllFavorites()
    }

    override fun onDestroy() {
        view = null
        compositeDisposable.dispose()
    }

    override fun refresh() {
        loadAllFavorites()
    }


    private fun loadAllFavorites(){
        val disposable = dataManager.getFavorites()
                .map {
                    it.onEach { it.isFavorite = true }
                }
                .map {
                    it.sortedWith(compareBy(Character::name))
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onFetchFavoritesSuccess, {})

        compositeDisposable.add(disposable)
    }

    override fun onFavoriteClick(character: Character) {
        character.isFavorite = !character.isFavorite
        if(character.isFavorite){
            dataManager.insertFavorite(character)
        } else {
            dataManager.deleteFavorite(character)
        }
    }

    override fun onListItemClick(character: Character, imageView: View) {
        view?.startDetailActivity(character, imageView)
    }


    /*
                Callbacks
             */
    private fun onFetchFavoritesSuccess(characterList: List<Character>) {
        view?.showFavorites(characterList)
    }

}