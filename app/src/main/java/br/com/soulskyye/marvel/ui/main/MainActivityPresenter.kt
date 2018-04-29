package br.com.soulskyye.marvel.ui.main

class MainActivityPresenter(private var view: MainActivityContract.View?) : MainActivityContract.Presenter {

    override fun start() {

    }

    override fun onDestroy() {
        view = null
    }

}