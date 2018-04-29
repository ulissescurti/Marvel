package br.com.soulskyye.marvel.utils

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.widget.AppCompatImageView
import br.com.soulskyye.marvel.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

/**
 * Method to load image into ImageView using picasso
 */
fun AppCompatImageView.loadImage(url: String?, callback: (Bitmap) -> Unit) {
    if (url != null && url.isNotBlank()) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.placeholder_character)
                .error(R.drawable.placeholder_character)
                .into(this, object : Callback {
                    override fun onSuccess() {
                        callback((drawable as BitmapDrawable).bitmap)
                    }

                    override fun onError(e: Exception?) {

                    }
                })
    } else {
        Picasso.get().load(R.drawable.placeholder_character)
    }
}