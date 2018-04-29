package br.com.soulskyye.marvel.ui.feature.main.fragment.character

import br.com.soulskyye.marvel.data.model.Character
import br.com.soulskyye.marvel.ui.base.BaseUiContract

interface CharacterListFragmentContract {

    interface View : BaseUiContract.View {

        fun addCharacters(results: ArrayList<Character>)
        fun showLoading()
        fun hideLoading()
        fun showLoadingFooter()
        fun showInternetError()
        fun showGenericError()
        fun hideTryAgain()
        fun refreshCharacters(results: ArrayList<Character>)
        fun clearList()
    }

    interface Presenter : BaseUiContract.Presenter {

        fun getCharacters(currentPage: Int)

        fun onTryAgainClick()
        fun refresh()
    }

}