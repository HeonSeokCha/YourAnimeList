package com.chs.youranimelist

import android.os.Build
import android.os.Build.VERSION_CODES.N
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton

object Binding {
    @BindingAdapter("imageSrc")
    @JvmStatic
    fun loadImg(imageView: ImageView,path: String) {
        Glide.with(imageView.context).load(path)
            .into(imageView)
    }

    @BindingAdapter("trailerBtn")
    @JvmStatic
    fun trailerChk(floatingActionButton: FloatingActionButton,trailer: String) {
        if(trailer == null) floatingActionButton.visibility = View.GONE
    }

    @BindingAdapter("htmlTags")
    @JvmStatic
    fun setHtmlTags(textView: TextView,string: String) {
        textView.text = Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY).toString()
    }
}