package xyz.ilyaxabibullin.curiosity.controllers

import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpView
import xyz.ilyaxabibullin.curiosity.entitys.CuriosityPhoto

interface MvpMainView: MvpView {
    fun showItem(items: ArrayList<CuriosityPhoto>)

    fun navigateToDetailView(cls: Class<*>,position: Int)

    fun showError(text: String)

    fun hideMainProgress()

    fun hideItemProgress()
}