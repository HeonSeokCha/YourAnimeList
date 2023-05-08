package com.chs.presentation

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.paging.compose.LazyPagingItems

val String.color
    get() = Color(android.graphics.Color.parseColor(this))


val Int?.isNotEmptyValue
    get() = this != null && this != 0

enum class SearchWidgetState {
    OPENED,
    CLOSED
}

fun <T : Any> LazyGridScope.items(
    items: LazyPagingItems<T>,
    key: ((item: T) -> Any)? = null,
    span: ((item: T) -> GridItemSpan)? = null,
    contentType: ((item: T) -> Any)? = null,
    itemContent: @Composable LazyGridItemScope.(value: T?) -> Unit
) {
    items(
        count = items.itemCount,
        key = if (key == null) null else { idx ->
            val item = items.peek(idx)
            if (item == null) {
                PagingPlaceholderKey(idx)
            } else {
                key(item)
            }
        },
        span = if (span == null) null else { idx ->
            val item = items.peek(idx)
            if (item == null) {
                GridItemSpan(1)
            } else {
                span(item)
            }
        },
        contentType = if (contentType == null) {
            { null }
        } else { idx ->
            val item = items.peek(idx)
            if (item == null) {
                null
            } else {
                contentType(item)
            }
        }
    ) { idx ->
        itemContent(items[idx])
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun <T : Any> LazyStaggeredGridScope.items(
    items: LazyPagingItems<T>,
    key: ((item: T) -> Any)? = null,
    span: ((item: T) -> StaggeredGridItemSpan)? = null,
    contentType: ((item: T) -> Any)? = null,
    itemContent: @Composable LazyStaggeredGridScope.(value: T?) -> Unit
) {
    items(
        count = items.itemCount,
        key = if (key == null) null else { idx ->
            val item = items.peek(idx)
            if (item == null) {
                PagingPlaceholderKey(idx)
            } else {
                key(item)
            }
        },
        span = if (span == null) null else { idx ->
            val item = items.peek(idx)
            if (item == null) {
                StaggeredGridItemSpan.FullLine
            } else {
                span(item)
            }
        },
        contentType = if (contentType == null) {
            { null }
        } else { idx ->
            val item = items.peek(idx)
            if (item == null) {
                null
            } else {
                contentType(item)
            }
        }
    ) { idx ->
        itemContent(items[idx])
    }
}

@SuppressLint("BanParcelableUsage")
private data class PagingPlaceholderKey(private val index: Int) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) = parcel.writeInt(index)

    override fun describeContents(): Int = 0

    companion object {
        @Suppress("unused")
        @JvmField
        val CREATOR: Parcelable.Creator<PagingPlaceholderKey> =
            object : Parcelable.Creator<PagingPlaceholderKey> {
                override fun createFromParcel(parcel: Parcel) =
                    PagingPlaceholderKey(parcel.readInt())

                override fun newArray(size: Int) = arrayOfNulls<PagingPlaceholderKey?>(size)
            }
    }
}