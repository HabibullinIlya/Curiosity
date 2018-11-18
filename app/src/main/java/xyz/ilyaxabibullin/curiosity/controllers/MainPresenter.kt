package xyz.ilyaxabibullin.curiosity.controllers

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import xyz.ilyaxabibullin.curiosity.entitys.CuriosityPhoto
import xyz.ilyaxabibullin.curiosity.entitys.Manifest
import java.net.UnknownHostException


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
            PhotoRepository.loadManifest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    manifest = it.manifest
                    day = manifest!!.photos.size - 1
                    loadPhoto()
                }, { t: Throwable ->
                    viewState.hideMainProgress()
                    this.unsuccessfulResult(t)
                })
        } else {
            loadPhoto()

        }
    }


    fun activityWasScrolled() {
        loadPhoto()

    }

    fun itemWasClicked(position: Int) {
        viewState.navigateToDetailView(DetailActivity::class.java, position)
    }

    private var a = 0
    private var b = a + count

    private var photos = ArrayList<CuriosityPhoto>()

    @SuppressLint("CheckResult")
    fun loadPhoto() {
        PhotoRepository.loadPhotos(manifest!!.photos[day].earthDate, page = page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
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
                        loadPhoto()
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
            }, { t: Throwable ->
                viewState.hideItemProgress()
                this.unsuccessfulResult(t)
            })
    }

    private fun unsuccessfulResult(t: Throwable) {
        t.printStackTrace()
        if (t is HttpException) {
            viewState.showError(t.code().toString())
        }
        else if( t is UnknownHostException){
            viewState.showError("check your internet connection")

        }else{
            viewState.showError("unknown error")
        }




    }

    //последняя страница за день
    private fun itLastPage(total: Int, page: Int): Boolean {
        val pageNum = if (total % count == 0) {
            total / count
        } else {
            total / count + 1
        }
        return pageNum == page

    }


}
