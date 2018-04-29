package br.com.soulskyye.marvel.utils

import android.util.DisplayMetrics
import android.view.WindowManager

object ScreenUtils {

    fun getScreenWidth(windowManager: WindowManager?): Int {
        val metrics = DisplayMetrics()
        windowManager?.defaultDisplay?.getMetrics(metrics)
        return metrics.widthPixels
    }

}