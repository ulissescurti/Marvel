package br.com.soulskyye.marvel.ui.feature.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.soulskyye.marvel.R
import br.com.soulskyye.marvel.ui.feature.main.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private lateinit var presenter: MainActivityContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()
        setupTabLayout()

        presenter = MainActivityPresenter(this)
        presenter.start()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
        supportActionBar?.setIcon(R.drawable.ic_launcher_foreground)
    }

    private fun setupTabLayout(){
        viewPager.adapter = ViewPagerAdapter(this, supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }
}
