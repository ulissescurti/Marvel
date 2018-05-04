package br.com.soulskyye.marvel.utils

import android.graphics.Bitmap
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import android.view.View

/**
 * Extension method that set the background color according to the received image
 */
fun View.setPaletteColor(image: Bitmap) {
    Palette.from(image).generate { palette ->
        val bgColor = palette.getDarkMutedColor(ContextCompat.getColor(context, android.R.color.black))
        setBackgroundColor(bgColor)
    }
}