package br.com.soulskyye.marvel.ui.feature.main.fragment.character

import android.animation.ValueAnimator
import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.util.Pair
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.view.animation.Animation
import br.com.soulskyye.marvel.R
import br.com.soulskyye.marvel.data.DataManager
import br.com.soulskyye.marvel.data.db.DatabaseManager
import br.com.soulskyye.marvel.data.model.Character
import br.com.soulskyye.marvel.data.network.ApiManager
import br.com.soulskyye.marvel.ui.feature.detail.CharacterDetailActivity
import br.com.soulskyye.marvel.ui.feature.main.fragment.character.adapter.CharacterListAdapter
import br.com.soulskyye.marvel.utils.EndlessRecyclerViewScrollListener
import br.com.soulskyye.marvel.utils.ScreenUtils
import kotlinx.android.synthetic.main.fragment_character_list.*


class CharacterListFragment : Fragment(), CharacterListFragmentContract.View, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var presenter: CharacterListFragmentContract.Presenter
    private var adapter: CharacterListAdapter? = null
    private var animatorFooter: ValueAnimator? = null
    private var tryAgainSnackBar: Snackbar? = null
    private var searchView: SearchView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        presenter = CharacterListFragmentPresenter(this, DataManager(ApiManager(), DatabaseManager()))
        presenter.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.main_menu, menu)

        val manager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView?.setSearchableInfo(manager.getSearchableInfo(activity?.componentName))
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                presenter.performSearch(query)
                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    private fun setupRecyclerView() {
        swipeRefreshCharacterList.setOnRefreshListener(this)

        val layoutManager = GridLayoutManager(context, 2)
        recyclerCharacterList.layoutManager = layoutManager
        recyclerCharacterList.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                presenter.getCharacters(page)
            }

            override fun canLoadMore(): Boolean {
                return presenter.canLoadMore()
            }
        })
    }


    /*
        SwipeRefresh Listener
     */
    override fun onRefresh() {
        hideTryAgain()
        searchView?.setQuery("", true)
        searchView?.clearFocus()
        searchView?.onActionViewCollapsed()
        swipeRefreshCharacterList.isRefreshing = true
        presenter.refresh()
    }

    /**
     * Add the given results to the characters list
     */
    override fun addCharacters(results: ArrayList<Character>, isFiltering: Boolean) {
        if (adapter == null) {
            adapter = CharacterListAdapter(results, presenter, ScreenUtils.getScreenWidth(activity?.windowManager))
            recyclerCharacterList.adapter = adapter
        } else {
            adapter?.addItems(results, isFiltering)
        }
    }

    /**
     * Clear the character list
     */
    override fun clearList() {
        adapter?.removeAll()
    }

    /**
     * Search the given term
     */
    override fun searchTerm(query: String?) {
        adapter?.filter?.filter(query)
    }

    /**
     * Refreshes the list of characters
     */
    override fun refreshCharacters(results: ArrayList<Character>) {
        adapter = CharacterListAdapter(results, presenter, ScreenUtils.getScreenWidth(activity?.windowManager))
        recyclerCharacterList.adapter = adapter
    }

    /**
     * Show the loading view at the middle of the screen
     */
    override fun showLoading() {
        lottieLoading.visibility = View.VISIBLE

        val animator = ValueAnimator.ofFloat(0f, 1f).setDuration(1500)
        animator.addUpdateListener { valueAnimator -> lottieLoading?.progress = valueAnimator.animatedValue as Float }

        animator.repeatMode = ValueAnimator.RESTART
        animator.repeatCount = ValueAnimator.INFINITE

        lottieLoading?.useHardwareAcceleration(true)
        lottieLoading?.enableMergePathsForKitKatAndAbove(true)

        animator.start()
    }

    /**
     * Show the loading view on footer of the screen
     */
    override fun showLoadingFooter() {
        animatorFooter = ValueAnimator.ofFloat(0F, 1F)
        animatorFooter?.duration = 2000

        val hsv = FloatArray(3)
        var runColor: Int

        hsv[1] = 1f
        hsv[2] = 1f
        animatorFooter?.addUpdateListener { animation ->
            hsv[0] = 360 * animation.animatedFraction

            runColor = Color.HSVToColor(hsv)
            viewFooterLoading?.setBackgroundColor(runColor)
        }

        animatorFooter?.repeatCount = Animation.INFINITE
        animatorFooter?.repeatMode = ValueAnimator.REVERSE

        animatorFooter?.start()
        viewFooterLoading.visibility = View.VISIBLE
    }

    /**
     *  Hide All loadings on screen
     */
    override fun hideLoading() {
        lottieLoading.visibility = View.GONE
        lottieLoading.cancelAnimation()
        viewFooterLoading.visibility = View.GONE
        animatorFooter?.end()
        swipeRefreshCharacterList.isRefreshing = false
    }

    /**
     * Show the error alert in case of internet error
     */
    override fun showInternetError() {
        showError(R.string.try_again_internet_error)
    }

    /**
     * Show the error alert in case of generic error
     */
    override fun showGenericError() {
        showError(R.string.try_again_list_generic_error)
    }

    /**
     * Hide the error alert
     */
    override fun hideTryAgain() {
        tryAgainSnackBar?.dismiss()
    }

    override fun startDetailActivity(character: Character?, imageView: android.view.View) {
        val intent = CharacterDetailActivity.newIntent(context!!, character, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            val p1 = Pair.create(imageView, getString(R.string.transition_character_image))
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity as Activity, p1)
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

    private fun showError(message: Int) {
        tryAgainSnackBar = Snackbar
                .make(coordinatorCharacterList, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.try_again) {
                    searchView?.setQuery("", true)
                    searchView?.clearFocus()
                    searchView?.onActionViewCollapsed()
                    presenter.onTryAgainClick()
                }

        tryAgainSnackBar?.show()
    }

}