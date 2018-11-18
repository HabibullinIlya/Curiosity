package xyz.ilyaxabibullin.curiosity.controllers

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import xyz.ilyaxabibullin.curiosity.App
import xyz.ilyaxabibullin.curiosity.entitys.CuriosityPhoto
import xyz.ilyaxabibullin.curiosity.entitys.Manifest
import xyz.ilyaxabibullin.curiosity.room.entitys.Photo
import java.net.UnknownHostException


@InjectViewState
class MainPresenter : MvpPresenter<MvpMainView>() {
    private val TAG = MainPresenter::class.java.name

    private var compositeDisposable = CompositeDisposable()
    private var page = 1

    private val count = 20

    private var manifest: Manifest? = null

    private var roomManifestList: ArrayList<xyz.ilyaxabibullin.curiosity.room.entitys.RoomManifest>? = null
    private var day = -1


    @SuppressLint("CheckResult")
    fun activityWasStarted() {
        println("on activityWasStarted")
        roomManifestList = loadManifestFromDb()
        if (roomManifestList!!.size == 0) {
            println("if room man. size = 0")
            this.loadManifestFromNet()
        } else {
            println("else it mean that manifest there is in db")
            //this.loadPhoto()
        }

    }

    @SuppressLint("CheckResult")
    private fun loadManifestFromNet() {
        PhotoRepository.loadManifest()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                manifest = it.manifest
                day = manifest!!.photos.size - 1
                this.saveManifestToDB(it.manifest)
                loadPhoto()

            }, { t: Throwable ->
                viewState.hideMainProgress()
                this.unsuccessfulResult(t)
            })
    }


    fun activityWasScrolled() {
        loadPhotoFromNet()

    }

    fun itemWasClicked(position: Int) {
        viewState.navigateToDetailView(DetailActivity::class.java, position)
    }

    private var a = 0
    private var b = a + count

    private var photos = ArrayList<CuriosityPhoto>()

    private fun loadPhoto() {
        var photosDb = ArrayList<Photo>()


        var end = false
        while (!end) {
            var it = this.loadPhotoFromDb()
            for (i in a until it.size) {
                photosDb.add(it[i])
            }
            when {
                it.size < count -> {
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
                }
                it.size > count -> {

                }

            }

        }

        for (rp in photosDb) {
            var tempPhoto = CuriosityPhoto()
            tempPhoto.imgSrc = rp.imgSrc
            tempPhoto.earthDate = rp.earthDate ?: "nevermore)"
            tempPhoto.id = rp.id
            photos.add(tempPhoto)
        }

    }

    private fun loadPhotoFromDb(): ArrayList<Photo> {
        return App.db.getRoomDao().findeByDateAndPage(manifest!!.photos[day].earthDate, page) as ArrayList<Photo>
    }

    @SuppressLint("CheckResult")
    fun loadPhotoFromNet() {
        var stringDate = manifest!!.photos[day].earthDate

        PhotoRepository.loadPhotos(stringDate, page = page)
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
                        loadPhotoFromNet()
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
        } else if (t is UnknownHostException) {
            viewState.showError("check your internet connection")

        } else {
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

    private fun saveManifestToDB(manifest: Manifest) {


    }

    private fun loadManifestFromDb(): ArrayList<xyz.ilyaxabibullin.curiosity.room.entitys.RoomManifest> {
        return App.db.getRoomDao().getAllManifests() as ArrayList<xyz.ilyaxabibullin.curiosity.room.entitys.RoomManifest>
    }


}
