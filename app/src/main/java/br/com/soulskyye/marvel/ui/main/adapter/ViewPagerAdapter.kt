package br.com.soulskyye.marvel.ui.main.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import br.com.soulskyye.marvel.R
import br.com.soulskyye.marvel.ui.main.fragment.character.CharacterListFragment
import br.com.soulskyye.marvel.ui.main.fragment.favorite.FavoriteListFragment
import java.util.*

class ViewPagerAdapter(private val context: Context,
                       private val manager: FragmentManager) : FragmentStatePagerAdapter(manager) {

    private val mFragmentsList: MutableList<Fragment> by lazy {
        ArrayList<Fragment>()
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> {
                val charactersFragment = CharacterListFragment()
                mFragmentsList.add(charactersFragment)
                charactersFragment
            }
            1 -> {
                val favoritesFragment = FavoriteListFragment()
                mFragmentsList.add(favoritesFragment)
                favoritesFragment
            }
            else -> null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.characters)
            1 -> context.getString(R.string.favorites)
            else -> null
        }
    }

}
