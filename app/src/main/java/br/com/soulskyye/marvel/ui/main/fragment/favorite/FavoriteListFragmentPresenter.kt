package br.com.soulskyye.marvel.ui.main.fragment.favorite

class FavoriteListFragmentPresenter(private var view: FavoriteListFragmentContract.View?) : FavoriteListFragmentContract.Presenter {

    override fun start() {

    }

    override fun onDestroy() {
        view = null
    }

}