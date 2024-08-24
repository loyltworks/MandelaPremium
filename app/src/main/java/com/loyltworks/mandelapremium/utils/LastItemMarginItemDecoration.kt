package com.loyltworks.mandelapremium.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LastItemMarginItemDecoration():RecyclerView.ItemDecoration() {

    private fun isLastItem(parent: RecyclerView, view: View, state: RecyclerView.State): Boolean {
        return parent.getChildAdapterPosition(view) == state.itemCount - 1
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            bottom = if (isLastItem(parent, view, state)) {
                260
            } else {
                0
            }
        }
    }
}