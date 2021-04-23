package com.chs.youranimelist.ui.browse.anime.characters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

object AnimeCharaBinding {

    @BindingAdapter("animeCharaCircle")
    @JvmStatic
    fun animeCharaCircle(imageView: ImageView, path: String?) {
        Glide.with(imageView.context).load(path)
            .apply(RequestOptions().circleCrop())
            .transition(DrawableTransitionOptions().crossFade())
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageView)
    }

}