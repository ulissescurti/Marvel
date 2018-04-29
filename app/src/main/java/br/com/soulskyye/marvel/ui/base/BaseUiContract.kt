package br.com.soulskyye.marvel.ui.base

interface BaseUiContract {
    interface View {

    }

    interface Presenter {
        fun start()
        fun onDestroy()
    }

}