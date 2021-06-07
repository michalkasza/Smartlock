package dev.michalkasza.smartlock.base

interface BaseView {
    val familiarName : String?
    fun getLinearVerticalLayoutManager(): androidx.recyclerview.widget.LinearLayoutManager
    fun getGridLayoutManager(): androidx.recyclerview.widget.GridLayoutManager
    fun showSnackbar(message: String)
    fun showSnackbar(messageResourceId: Int)
}