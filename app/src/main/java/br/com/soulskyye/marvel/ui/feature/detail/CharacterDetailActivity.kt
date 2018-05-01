package br.com.soulskyye.marvel.ui.feature.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import br.com.soulskyye.marvel.R
import br.com.soulskyye.marvel.data.DataManager
import br.com.soulskyye.marvel.data.db.DatabaseManager
import br.com.soulskyye.marvel.data.model.Character
import br.com.soulskyye.marvel.data.model.Thumbnail
import br.com.soulskyye.marvel.data.network.ApiManager
import br.com.soulskyye.marvel.utils.loadImage
import br.com.soulskyye.marvel.utils.setPaletteColor
import br.com.soulskyye.marvelheroes.ui.detail.CharacterDetailActivityContract
import br.com.soulskyye.marvelheroes.ui.detail.CharacterDetailActivityPresenter
import kotlinx.android.synthetic.main.activity_character_detail.*


class CharacterDetailActivity : AppCompatActivity(), CharacterDetailActivityContract.View {

    companion object {

        const val EXTRA_CHARACTER = "EXTRA_CHARACTER"

        @JvmStatic
        fun newIntent(context: Context, character: br.com.soulskyye.marvel.data.model.Character): Intent {

            val intent = Intent(context, CharacterDetailActivity::class.java)
            intent.putExtra(EXTRA_CHARACTER, character)
            return intent
        }
    }

    private var presenter: CharacterDetailActivityContract.Presenter? = null

    private var menu: Menu? = null


    //Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)

        setupActionBar()

        presenter = CharacterDetailActivityPresenter(this, intent.getSerializableExtra(EXTRA_CHARACTER) as Character, DataManager(ApiManager(), DatabaseManager()))
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
    override fun showImage(thumbnail: Thumbnail?) {
        imageViewCharacterDetail.loadImage("${thumbnail?.path}.${thumbnail?.extension}", {
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
}
