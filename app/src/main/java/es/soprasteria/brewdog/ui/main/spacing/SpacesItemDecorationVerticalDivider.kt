package es.inmovens.selyco.ui.adapters.spacing

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import es.soprasteria.brewdog.R


class SpacesItemDecorationVerticalDivider(
    private val context: Context,
    private val spaceTopBottom: Int,
    private val spaceBetweenElements: Int,
    private val spaceLeftRight: Int
) : RecyclerView.ItemDecoration() {


    private val mDivider: Drawable = ContextCompat.getDrawable(context, R.drawable.item_divider)!!


    override fun onDraw(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            if (i == 0) {
                drawTopDivider(c, parent, i)
                drawBottomDivider(c, parent, i)
            } else {
                drawBottomDivider(c, parent, i)
            }
        }
    }


    private fun drawBottomDivider(c: Canvas, parent: RecyclerView, i: Int) {

        val child = parent.getChildAt(i)

        val params = child.layoutParams as RecyclerView.LayoutParams

        val left = spaceLeftRight
        val right = parent.width - spaceLeftRight

        val verticalSpace = if (i == parent.childCount - 1) {
            // Last element
            spaceTopBottom
        } else {
            spaceBetweenElements
        }

        val top = child.bottom + params.bottomMargin + (verticalSpace / 2)

        val bottom: Int = top + mDivider.intrinsicHeight

        mDivider.setBounds(left, top, right, bottom)
        mDivider.draw(c)
    }


    private fun drawTopDivider(c: Canvas, parent: RecyclerView, i: Int) {

        val child = parent.getChildAt(i)

        val params = child.layoutParams as RecyclerView.LayoutParams

        val left = spaceLeftRight
        val right = parent.width - spaceLeftRight

        val top = child.top - params.topMargin - (spaceTopBottom / 2)

        val bottom: Int = top + mDivider.intrinsicHeight

        mDivider.setBounds(left, top, right, bottom)
        mDivider.draw(c)
    }


}