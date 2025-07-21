package com.paras_test_android.assignment_paras

import android.graphics.Rect
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher


/**
 * RecyclerViewMatcher  helper class to help with matching views inside RecyclerView
 * for the instrumented tests

 */
class RecyclerViewMatcher(private val recyclerViewId: Int) {

    fun atPositionOnView(position: Int, targetViewId: Int): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

            override fun describeTo(description: Description) {
                description.appendText("has view id $targetViewId at position $position")
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                    ?:
                    return false
                val targetView = viewHolder.itemView.findViewById<View>(targetViewId)
                return targetView != null && targetView is EditText && targetView.visibility == View.VISIBLE && targetView.getGlobalVisibleRect(
                    Rect()
                )
            }
        }
    }
}