package com.stkent.recyclerviewdragdroptesting

sealed class RowType {
    data class Header(val id: Int) : RowType() {
        override fun toString() = "Header $id"
    }

    data class Content(val id: String) : RowType() {
        override fun toString() = "Content $id"
    }
}
