package com.stkent.recyclerviewdragdroptesting

import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stkent.recyclerviewdragdroptesting.RowType.Content
import com.stkent.recyclerviewdragdroptesting.RowType.Header

sealed class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    class HeaderViewHolder(itemView: View) : CustomViewHolder(itemView) {
        private val label = itemView.findViewById<TextView>(R.id.header_label)

        fun bind(data: Header) {
            label.text = data.toString()
        }
    }

    class ContentViewHolder(
        itemView: View,
        dragStartListener: (RecyclerView.ViewHolder) -> Unit
    ) : CustomViewHolder(itemView) {

        private val label = itemView.findViewById<TextView>(R.id.content_label)
        private val dragHandle = itemView.findViewById<View>(R.id.drag_handle)

        init {
            dragHandle.setOnTouchListener { _, motionEvent ->
                if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                    dragStartListener(this)
                }

                false
            }
        }

        fun bind(data: Content) {
            label.text = data.toString()
        }
    }

}
