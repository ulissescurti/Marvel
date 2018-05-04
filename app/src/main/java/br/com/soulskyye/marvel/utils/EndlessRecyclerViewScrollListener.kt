package br.com.soulskyye.marvel.utils

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager

/**
 *  Adapted from https://gist.github.com/nesquena/d09dc68ff07e845cc622
 */
abstract class EndlessRecyclerViewScrollListener(layoutManager: GridLayoutManager) : RecyclerView.OnScrollListener() {

    companion object {
        const val REQUEST_LIMIT = 20
    }

    private var visibleThreshold = REQUEST_LIMIT
    private var currentPage = 0
    private var previousTotalItemCount = 0
    private var loading = true
    private val startingPageIndex = 0

    private var mLayoutManager: RecyclerView.LayoutManager = layoutManager

    override fun onScrolled(view: RecyclerView?, dx: Int, dy: Int) {
        if(!canLoadMore()){
            loading = false
            return
        }

        val totalItemCount = mLayoutManager.itemCount

        val lastVisibleItemPosition = (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()

        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMore(currentPage, totalItemCount, view)
            loading = true
        }
    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?)
    abstract fun canLoadMore(): Boolean

    init {
        visibleThreshold *= layoutManager.spanCount
    }
}