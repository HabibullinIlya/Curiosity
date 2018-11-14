package xyz.ilyaxabibullin.curiosity.controllers

import android.annotation.SuppressLint
import android.util.Log
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
                        Log.d(TAG, "${manifest!!.photos[day].totalPhotos} total photos tipa")


                        alternateLoadPhoto()

                        /*PhotoRepository.loadPhotos(
                            it.manifest
                                .photos[it.manifest.photos.size - 1].earthDate
                        )
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe { it ->

                                viewState.showItem(it.photos)
                            }*/
                    }
            )
        } else {
            /*compositeDisposable.add(
                PhotoRepository.loadPhotos(
                    manifest!!
                        .photos[manifest!!.photos.size - 1].earthDate
                )
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe { it ->
                        viewState.showItem(it.photos)
                    }
            )*/

            alternateLoadPhoto()

        }


    }

    fun activityWasScrolled() {

        alternateLoadPhoto()
        /*Log.d(TAG, "presenter:")
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
        )*/
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

                println("полученнные по сети")
                it.photos.forEach { p ->
                    println(p.id)
                }

                println("before   a = $a  b = $b   ${photos.size}")
                //photos.addAll(it.photos)
                for (i in a until it.photos.size) {
                    photos.add(it.photos[i])
                }
                when {
                    photos.size < count -> {
                        println("when < 20   a = $a  b = $b   ${photos.size}")
                        if ((manifest!!.photos[day].totalPhotos > 25)&&(!itLastPage(manifest!!.photos[day].totalPhotos,page))) {
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
                        println("when > 20 a = $a  b = $b   ${photos.size}")
                        var tempPhotos = ArrayList<CuriosityPhoto>()

                        for (i in a until b) {
                            tempPhotos.add(photos[i])
                        }
                        a = b
                        b += count


                        photos = tempPhotos
                        println("отправленные на UI  ${photos.size}")
                        photos.forEach { p ->
                            println(p.id)
                        }
                        viewState.showItem(photos)
                        photos = ArrayList()

                    }
                    else -> {
                        println("отправленные на UI")
                        println(" else  = $a  b = $b   ${photos.size}")
                        photos.forEach { p ->
                            println(p.id)
                        }
                        viewState.showItem(photos)

                    }
                }
            }
    }

    fun itLastPage(total: Int, page: Int): Boolean {
        var pageNum = 0
        pageNum = if (total % count == 0) {
            total / count
        } else {
            total / count + 1
        }
        return pageNum == page

    }


}
