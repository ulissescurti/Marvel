package br.com.soulskyye.marvel.ui.main.fragment.character

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.soulskyye.marvel.R
import br.com.soulskyye.marvel.data.DataManager
import br.com.soulskyye.marvel.data.model.Character
import br.com.soulskyye.marvel.data.network.ApiManager
import br.com.soulskyye.marvel.ui.main.fragment.character.adapter.CharacterListAdapter
import br.com.soulskyye.marvel.utils.EndlessRecyclerViewScrollListener
import br.com.soulskyye.marvel.utils.ScreenUtils
import kotlinx.android.synthetic.main.fragment_character_list.*
import android.view.animation.Animation



class CharacterListFragment : Fragment(), CharacterListFragmentContract.View {

    private lateinit var presenter: CharacterListFragmentContract.Presenter
    private var adapter: CharacterListAdapter? = null

    private var animatorFooter: ValueAnimator? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        presenter = CharacterListFragmentPresenter(this, DataManager(ApiManager()))
        presenter.start()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(context, 2)
        recyclerCharacterList.layoutManager = layoutManager
        recyclerCharacterList.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onScrolled(view: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(view, dx, dy)
            }

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                presenter.getCharacters(page)
            }
        })
    }


    /*
        View Contract
     */
    override fun addCharacters(results: ArrayList<Character>) {
        if (adapter == null) {
            adapter = CharacterListAdapter(results, context, presenter, ScreenUtils.getScreenWidth(activity?.windowManager))
            recyclerCharacterList.adapter = adapter
        } else {
            adapter?.addItems(results)
        }
    }

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

    override fun showLoadingFooter() {
        animatorFooter = ValueAnimator.ofFloat(0F, 1F)
        animatorFooter?.duration = 2000

        val hsv: FloatArray
        var runColor: Int
        val hue = 0
        hsv = FloatArray(3)
        hsv[1] = 1f
        hsv[2] = 1f
        animatorFooter?.addUpdateListener { animation ->
            hsv[0] = 360 * animation.animatedFraction

            runColor = Color.HSVToColor(hsv)
            viewFooterLoading.setBackgroundColor(runColor)
        }

        animatorFooter?.repeatCount = Animation.INFINITE
        animatorFooter?.repeatMode = ValueAnimator.REVERSE

        animatorFooter?.start()
        viewFooterLoading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        lottieLoading.visibility = View.GONE
        lottieLoading.cancelAnimation()
        viewFooterLoading.visibility = View.GONE
        animatorFooter?.end()
    }

}