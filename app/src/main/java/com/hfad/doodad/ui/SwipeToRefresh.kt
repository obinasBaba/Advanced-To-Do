package com.hfad.doodad.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


class SwipeToRefresh( ctx : Context, attset : AttributeSet ) : SwipeRefreshLayout( ctx, attset )
{
    val childView : View? = null

    override fun canChildScrollUp(): Boolean {
        return childView?.canScrollVertically(-1) ?:  super.canChildScrollUp()
    }
}