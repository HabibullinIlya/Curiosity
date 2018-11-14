package xyz.ilyaxabibullin.curiosity.controllers

import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.ilyaxabibullin.curiosity.App
import xyz.ilyaxabibullin.curiosity.entitys.CuriosityPhoto
import xyz.ilyaxabibullin.curiosity.entitys.Manifest
import xyz.ilyaxabibullin.curiosity.entitys.ManifestResponce
import xyz.ilyaxabibullin.curiosity.entitys.Photos
import xyz.ilyaxabibullin.curiosity.network.NASAApi
import java.util.*

object PhotoRepository {
    var photos = ArrayList<CuriosityPhoto>()
    private lateinit var manifest: Manifest


    fun loadPhotos(date:String,page:Int):Observable<Photos>{
        return App.nasaApi.getPhotos(date,key = App.apiKey,page = page)
    }



    fun loadManifest():Observable<ManifestResponce>{
        return App.nasaApi.getManifest(App.apiKey)
    }


}