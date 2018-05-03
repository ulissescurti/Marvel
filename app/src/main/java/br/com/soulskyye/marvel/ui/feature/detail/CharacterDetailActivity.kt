package br.com.soulskyye.marvel.ui.feature.detail

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import br.com.soulskyye.marvel.R
import br.com.soulskyye.marvel.data.DataManager
import br.com.soulskyye.marvel.data.db.DatabaseManager
import br.com.soulskyye.marvel.data.model.Character
import br.com.soulskyye.marvel.data.model.Item
import br.com.soulskyye.marvel.data.model.Thumbnail
import br.com.soulskyye.marvel.data.network.ApiManager
import br.com.soulskyye.marvel.ui.feature.detail.adapter.DetailItemListAdapter
import br.com.soulskyye.marvel.utils.loadImage
import br.com.soulskyye.marvel.utils.setPaletteColor
import br.com.soulskyye.marvelheroes.ui.detail.CharacterDetailActivityContract
import br.com.soulskyye.marvelheroes.ui.detail.CharacterDetailActivityPresenter
import com.google.gson.Gson
import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_character_detail.*
import kotlinx.android.synthetic.main.fragment_character_list.*


class CharacterDetailActivity : AppCompatActivity(), CharacterDetailActivityContract.View {

    companion object {
        const val EXTRA_CHARACTER_ID = "EXTRA_CHARACTER_ID"
        const val EXTRA_CHARACTER_IMAGE = "EXTRA_CHARACTER_IMAGE"
        const val EXTRA_CHARACTER = "EXTRA_CHARACTER"

        @JvmStatic
        fun newIntent(context: Context, characterId: String?, characterImage: String, character: Character?): Intent {

            val intent = Intent(context, CharacterDetailActivity::class.java)
            intent.putExtra(EXTRA_CHARACTER_ID, characterId)
            intent.putExtra(EXTRA_CHARACTER_IMAGE, characterImage)
            character?.let {
                intent.putExtra(EXTRA_CHARACTER, Gson().toJson(character))
            }
            return intent
        }
    }

    private var presenter: CharacterDetailActivityContract.Presenter? = null

    private var menu: Menu? = null

    private var snackBarDetail: Snackbar? = null

    //Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)

        setupActionBar()


        presenter = CharacterDetailActivityPresenter(this,
                Gson().fromJson(intent.getStringExtra(EXTRA_CHARACTER), Character::class.java),
                intent.getStringExtra(EXTRA_CHARACTER_ID),
                intent.getStringExtra(EXTRA_CHARACTER_IMAGE),
                DataManager(ApiManager(), DatabaseManager()))
        presenter?.start()
    }

    override fun onDestroy() {
        presenter?.onDestroy()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> super.onBackPressed()
            R.id.action_favorite -> presenter?.onFavoriteClick()
        }
        return super.onOptionsItemSelected(menuItem)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.character_menu, menu)

        presenter?.updateFavoriteMenu()

        return super.onCreateOptionsMenu(menu)
    }



    override fun onNavigateUp(): Boolean {
        finish()
        return true
    }



    private fun setupActionBar(){
        setSupportActionBar(toolbarDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }



    /*
        View Contract
     */
    override fun showImage(image: String) {
        imageViewCharacterDetail.loadImage(image,
                R.drawable.placeholder_character,
                {
                    scrollViewCharacterDetail.setPaletteColor(it)
                })
    }

    override fun showName(name: String) {
        supportActionBar?.title = name
        textViewCharacterDetailName.text = name
    }

    override fun showDescription(description: String) {
        textViewCharacterDetailDescription.text = description
    }

    override fun showFavoriteIcon() {
        val item = menu?.findItem(R.id.action_favorite)
        item?.icon = ContextCompat.getDrawable(baseContext, R.drawable.star_filled_white)
    }

    override fun showFavoriteEmptyIcon() {
        val item = menu?.findItem(R.id.action_favorite)
        item?.icon = ContextCompat.getDrawable(baseContext, R.drawable.star_bordered_white)
    }

    override fun showComics(items: List<Item>) {
        val layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewComics.layoutManager = layoutManager
        recyclerViewComics.adapter = DetailItemListAdapter((items as RealmList<Item>).toMutableList())
    }

    override fun hideComics() {
        textViewCharacterDetailComics.visibility = View.GONE
        recyclerViewComics.visibility = View.GONE
    }

    override fun showSeries(items: List<Item>) {
        val layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewSeries.layoutManager = layoutManager
        recyclerViewSeries.adapter = DetailItemListAdapter((items as RealmList<Item>).toMutableList())
    }

    override fun hideSeries() {
        textViewCharacterDetailSeries.visibility = View.GONE
        recyclerViewSeries.visibility = View.GONE
    }

    override fun showLoading() {
        linearDetails.visibility = View.GONE
        lottieLoadingDetail.visibility = View.VISIBLE

        val animator = ValueAnimator.ofFloat(0f, 1f).setDuration(1500)
        animator.addUpdateListener { valueAnimator -> lottieLoadingDetail?.progress = valueAnimator.animatedValue as Float }

        animator.repeatMode = ValueAnimator.RESTART
        animator.repeatCount = ValueAnimator.INFINITE

        lottieLoadingDetail?.useHardwareAcceleration(true)
        lottieLoadingDetail?.enableMergePathsForKitKatAndAbove(true)

        animator.start()
    }

    override fun hideLoading() {
        lottieLoadingDetail.visibility = View.GONE
        lottieLoadingDetail.cancelAnimation()
        linearDetails.visibility = View.VISIBLE
    }

    override fun showInternetError() {
        showError(R.string.try_again_internet_error)
    }

    override fun showGenericError() {
        showError(R.string.try_again_detail_generic_error)
    }

    override fun hideTryAgain() {
        snackBarDetail?.dismiss()
    }


    /*
        Util
     */
    private fun showError(message: Int) {
        snackBarDetail = Snackbar
                .make(coordinatorDetail, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.try_again) {
                    presenter?.onTryAgainClick()
                }

        snackBarDetail?.show()
    }
}
