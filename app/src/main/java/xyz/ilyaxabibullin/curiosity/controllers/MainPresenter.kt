package xyz.ilyaxabibullin.curiosity.controllers

import android.annotation.SuppressLint
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import xyz.ilyaxabibullin.curiosity.App

import xyz.ilyaxabibullin.curiosity.entitys.CuriosityPhoto
import xyz.ilyaxabibullin.curiosity.entitys.Manifest
import xyz.ilyaxabibullin.curiosity.entitys.ManifestResponce

import java.util.*
import kotlin.collections.ArrayList


@InjectViewState
class MainPresenter : MvpPresenter<MvpMainView>() {
    val TAG = MainPresenter::class.java.name

    var compositeDisposable = CompositeDisposable()
    var page = 0


    var manifest: Manifest? = null
    var day = -1


    @SuppressLint("CheckResult")
    fun activityWasStarted() {

        if (manifest == null) {
            compositeDisposable.add(
                PhotoRepository.loadManifest()
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        manifest = it.manifest
                        day = manifest!!.photos.size - 1
                        Log.d(TAG, "${manifest!!.photos[day].totalPhotos} total photos tipa")

                        PhotoRepository.loadPhotos(
                            it.manifest
                                .photos[it.manifest.photos.size - 1].earthDate
                        )
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe { it ->

                                viewState.showItem(it.photos)
                            }
                    }
            )
        } else {
            compositeDisposable.add(
                PhotoRepository.loadPhotos(
                    manifest!!
                        .photos[manifest!!.photos.size - 1].earthDate
                )
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe { it ->
                        viewState.showItem(it.photos)
                    }
            )

        }


    }

    fun activityWasScrolled() {
        Log.d(TAG, "presenter:")
        day -= 1
        println(day)
        println(
            manifest!!
                .photos[day].earthDate
        )
        compositeDisposable.add(
            PhotoRepository.loadPhotos(
                manifest!!
                    .photos[day].earthDate
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { it ->
                    viewState.showItem(it.photos)
                }
        )
    }

    fun itemWasClicked(position:Int){
        viewState.navigateToDetailView(DetailActivity::class.java,position)
    }


}
