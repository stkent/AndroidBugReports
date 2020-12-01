package com.stkent.recyclerviewdragdroptesting

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stkent.recyclerviewdragdroptesting.CustomViewHolder.ContentViewHolder
import com.stkent.recyclerviewdragdroptesting.CustomViewHolder.HeaderViewHolder
import com.stkent.recyclerviewdragdroptesting.RowType.Content
import com.stkent.recyclerviewdragdroptesting.RowType.Header

class DiffingAdapter(
    private val dragStartListener: (RecyclerView.ViewHolder) -> Unit
) : ListAdapter<RowType, CustomViewHolder>(diffCallback) {

    companion object {
        private val LOG_TAG = DiffingAdapter::class.java.simpleName

        private val diffCallback = object : DiffUtil.ItemCallback<RowType>() {
            override fun areItemsTheSame(oldItem: RowType, newItem: RowType): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: RowType, newItem: RowType): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> HeaderViewHolder(inflater.inflate(R.layout.header, parent, false))

            1 -> ContentViewHolder(
                inflater.inflate(R.layout.content, parent, false),
                dragStartListener
            )

            else -> error("Unexpected view type $viewType.")
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val data = currentList[position]
        when (holder) {
            is HeaderViewHolder -> holder.bind(data as Header)
            is ContentViewHolder -> holder.bind(data as Content)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is Header -> 0
            is Content -> 1
        }
    }

    fun move(fromIndex: Int, toIndex: Int) {
        Log.v(LOG_TAG, "::move($fromIndex, $toIndex).")
        Log.v(LOG_TAG, "Initial list: $currentList.")

        if (fromIndex == toIndex) {
            Log.v(LOG_TAG, "No-op.")
            return
        }

        val listCopy = mutableListOf<RowType>().apply { addAll(currentList) }

        val movingRow = listCopy.removeAt(fromIndex)
        listCopy.add(toIndex, movingRow)

        Log.v(LOG_TAG, "Updated list: $listCopy.")
        submitList(listCopy)
    }
}
