package br.com.soulskyye.marvel.utils

import android.util.DisplayMetrics
import android.view.WindowManager

object ScreenUtils {

    /**
     * Method that returns the full width of the device screen
     */
    fun getScreenWidth(windowManager: WindowManager?): Int {
        val metrics = DisplayMetrics()
        windowManager?.defaultDisplay?.getMetrics(metrics)
        return metrics.widthPixels
    }

}