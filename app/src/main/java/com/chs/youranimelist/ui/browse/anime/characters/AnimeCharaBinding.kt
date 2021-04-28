package com.chs.youranimelist.ui.browse.anime.characters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation

object AnimeCharaBinding {

    @BindingAdapter("animeCharaCircle")
    @JvmStatic
    fun animeCharaCircle(imageView: ImageView, path: String?) {
        imageView.load(path) {
            transformations(CircleCropTransformation())
            size(200, 200)
            crossfade(true)
        }
    }
}