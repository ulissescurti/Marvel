package br.com.soulskyye.marvel.ui.main.fragment.character

import br.com.soulskyye.marvel.data.model.Character
import br.com.soulskyye.marvel.ui.base.BaseUiContract

interface CharacterListFragmentContract {

    interface View : BaseUiContract.View {

        fun addCharacters(results: ArrayList<Character>)
        fun showLoading()
        fun hideLoading()
        fun showLoadingFooter()
    }

    interface Presenter : BaseUiContract.Presenter {

        fun getCharacters(currentPage: Int)

    }

}