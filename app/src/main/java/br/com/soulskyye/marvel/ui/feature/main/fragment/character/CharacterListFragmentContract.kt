package br.com.soulskyye.marvel.ui.feature.main.fragment.character

import br.com.soulskyye.marvel.data.model.Character
import br.com.soulskyye.marvel.ui.base.BaseUiContract
import br.com.soulskyye.marvel.ui.feature.main.fragment.base.BaseCharacterContract

interface CharacterListFragmentContract {

    interface View : BaseUiContract.View, BaseCharacterContract.View {

        fun addCharacters(results: ArrayList<Character>, isFiltering: Boolean)
        fun showLoading()
        fun hideLoading()
        fun showLoadingFooter()
        fun showInternetError()
        fun showGenericError()
        fun hideTryAgain()
        fun refreshCharacters(results: ArrayList<Character>)
        fun clearList()
        fun searchTerm(query: String?)
    }

    interface Presenter : BaseUiContract.Presenter, BaseCharacterContract.Presenter {

        fun getCharacters(currentPage: Int)

        fun onTryAgainClick()
        fun performSearch(query: String?)
        fun canLoadMore(): Boolean
    }

}