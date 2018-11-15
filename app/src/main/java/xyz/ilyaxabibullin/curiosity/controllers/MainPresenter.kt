package xyz.ilyaxabibullin.curiosity.controllers

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import xyz.ilyaxabibullin.curiosity.entitys.CuriosityPhoto
import xyz.ilyaxabibullin.curiosity.entitys.Manifest


@InjectViewState
class MainPresenter : MvpPresenter<MvpMainView>() {
    private val TAG = MainPresenter::class.java.name

    private var compositeDisposable = CompositeDisposable()
    private var page = 1

    private val count = 20


    private var manifest: Manifest? = null
    private var day = -1


    @SuppressLint("CheckResult")
    fun activityWasStarted() {

        if (manifest == null) {
            compositeDisposable.add(
                PhotoRepository.loadManifest()
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        manifest = it.manifest
                        day = manifest!!.photos.size - 1

                        alternateLoadPhoto()

                    }
            )
        } else {
            alternateLoadPhoto()

        }


    }

    fun activityWasScrolled() {
        alternateLoadPhoto()

    }

    fun itemWasClicked(position: Int) {
        viewState.navigateToDetailView(DetailActivity::class.java, position)
    }

    private var a = 0
    private var b = a + count

    private var photos = ArrayList<CuriosityPhoto>()

    @SuppressLint("CheckResult")
    fun alternateLoadPhoto() {
        PhotoRepository.loadPhotos(manifest!!.photos[day].earthDate, page = page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

                for (i in a until it.photos.size) {
                    photos.add(it.photos[i])
                }
                when {
                    photos.size < count -> {
                        val total = manifest!!.photos[day].totalPhotos

                        if ((total > 25) && (!itLastPage(total, page))) {
                            page++
                            a = 0
                            b = count
                        } else {
                            a = 0
                            b = count
                            page = 1
                            day--
                        }
                        alternateLoadPhoto()
                    }
                    photos.size > count -> {

                        var tempPhotos = ArrayList<CuriosityPhoto>()

                        for (i in a until b) {
                            tempPhotos.add(photos[i])
                        }
                        a = b
                        b += count


                        photos = tempPhotos

                        photos.forEach { p ->
                            println(p.id)
                        }
                        viewState.showItem(photos)
                        photos = ArrayList()

                    }
                    else -> {

                        viewState.showItem(photos)

                    }
                }
            }
    }

    private fun itLastPage(total: Int, page: Int): Boolean {
        val pageNum = if (total % count == 0) {
            total / count
        } else {
            total / count + 1
        }
        return pageNum == page

    }


}
