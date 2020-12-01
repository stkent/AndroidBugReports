package com.stkent.recyclerviewdragdroptesting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.DOWN
import androidx.recyclerview.widget.ItemTouchHelper.UP
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.stkent.recyclerviewdragdroptesting.RowType.Content
import com.stkent.recyclerviewdragdroptesting.RowType.Header

class MainActivity : AppCompatActivity() {

    private val dragHelper = ItemTouchHelper(
        object : ItemTouchHelper.SimpleCallback(UP or DOWN, 0) {
            override fun isLongPressDragEnabled() = false

            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                // Not supported.
            }

            override fun canDropOver(rv: RecyclerView, drag: ViewHolder, target: ViewHolder) = true

            override fun onMove(rv: RecyclerView, drag: ViewHolder, target: ViewHolder): Boolean {
                (rv.adapter as DiffingAdapter).move(drag.layoutPosition, target.layoutPosition)
                return true
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = null

        dragHelper.attachToRecyclerView(recyclerView)

        val adapter = DiffingAdapter(dragHelper::startDrag)
        recyclerView.adapter = adapter

        adapter.submitList(
            listOf(
                Header( id =  1 ),
                Content(id = "A"),
                Content(id = "B"),
                Header( id =  2 ),
                Content(id = "C"),
                Content(id = "D"),
                Header( id =  3 ),
                Header( id =  4 ),
                Header( id =  5 ),
                Content(id = "E"),
                Content(id = "F"),
                Content(id = "G"),
                Content(id = "H"),
                Content(id = "I"),
                Content(id = "J"),
                Content(id = "K"),
                Content(id = "L"),
                Content(id = "M"),
                Content(id = "N"),
                Content(id = "O"),
                Content(id = "P"),
                Content(id = "Q"),
                Content(id = "R"),
                Content(id = "S"),
                Content(id = "T"),
                Content(id = "U"),
                Content(id = "V"),
                Content(id = "W"),
                Content(id = "X"),
                Content(id = "Y"),
                Content(id = "Z"),
                Header( id =  6 ),
            )
        )
    }

    override fun onDestroy() {
        dragHelper.attachToRecyclerView(null)
        super.onDestroy()
    }

}
