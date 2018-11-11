package xyz.ilyaxabibullin.curiosity.controllers

import android.annotation.SuppressLint
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
import xyz.ilyaxabibullin.curiosity.entitys.ManifestResponce

import java.util.*
import kotlin.collections.ArrayList


@InjectViewState
class MainPresenter : MvpPresenter<MvpMainView>() {

    var compositeDisposable = CompositeDisposable()
    var page = 0

    @SuppressLint("CheckResult")
    fun activityWasStarted() {
        compositeDisposable.add(PhotoRepository.loadManifest()
            .subscribeOn(Schedulers.io())
            .subscribe{
                PhotoRepository.loadPhotos(it.manifest
                    .photos[it.manifest.photos.size-1].earthDate)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe{
                        viewState.showItem(it.photos)
                    }
            }
        )
    }


}
