package xyz.ilyaxabibullin.curiosity.controllers

import com.arellomobile.mvp.MvpView
import xyz.ilyaxabibullin.curiosity.entitys.CuriosityPhoto

interface MvpMainView: MvpView {
    fun showItem(items: ArrayList<CuriosityPhoto>)


}