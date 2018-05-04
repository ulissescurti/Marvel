package br.com.soulskyye.marvel.ui.feature.main.fragment.favorite

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.util.Pair
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.soulskyye.marvel.R
import br.com.soulskyye.marvel.data.DataManager
import br.com.soulskyye.marvel.data.db.DatabaseManager
import br.com.soulskyye.marvel.data.model.Character
import br.com.soulskyye.marvel.data.network.ApiManager
import br.com.soulskyye.marvel.ui.feature.detail.CharacterDetailActivity
import br.com.soulskyye.marvel.ui.feature.main.fragment.character.adapter.CharacterListAdapter
import br.com.soulskyye.marvel.utils.ScreenUtils
import kotlinx.android.synthetic.main.fragment_favorite_list.*

class FavoriteListFragment : Fragment(), FavoriteListFragmentContract.View, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var presenter: FavoriteListFragmentContract.Presenter
    private var adapter: CharacterListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        presenter = FavoriteListFragmentPresenter(this, DataManager(ApiManager(), DatabaseManager()))
        presenter.start()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    private fun setupRecyclerView() {
        swipeRefreshFavoriteList.setOnRefreshListener(this)

        val layoutManager = GridLayoutManager(context, 2)
        recyclerFavoriteList.layoutManager = layoutManager
    }


    /*
        SwipeRefresh Listener
     */
    override fun onRefresh() {
        swipeRefreshFavoriteList.isRefreshing = true
        presenter.refresh()
    }


    /*
        View Contract
     */
    override fun showFavorites(characterList: List<Character>) {
        swipeRefreshFavoriteList.isRefreshing = false

        if (characterList.isNotEmpty()) {
            textViewNoFavoritesFound.visibility = View.GONE
        } else {
            textViewNoFavoritesFound.visibility = View.VISIBLE
        }

        if (adapter != null) {
            adapter?.removeAll()
        }

        val list = ArrayList<Character>()
        if (characterList.isNotEmpty()) {
            list.addAll(characterList)
        }

        adapter = CharacterListAdapter(list, presenter, ScreenUtils.getScreenWidth(activity?.windowManager))
        recyclerFavoriteList.adapter = adapter
    }

    override fun startDetailActivity(character: Character?, imageView: android.view.View) {
        val intent = CharacterDetailActivity.newIntent(context!!, character, true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            val p1 = Pair.create(imageView, getString(R.string.transition_character_image))
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity as Activity, p1)
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

}