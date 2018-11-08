package xyz.ilyaxabibullin.curiosity.controllers

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.ilyaxabibullin.curiosity.App
import xyz.ilyaxabibullin.curiosity.entitys.CuriosityPhoto
import xyz.ilyaxabibullin.curiosity.entitys.Photos
import xyz.ilyaxabibullin.curiosity.network.NASAApi


@InjectViewState
class MainPresenter : MvpPresenter<MvpMainView>() {

    fun activityWasStarted() {
        App.retrofit.create(NASAApi::class.java).getPhotos("2015-6-3",  "DEMO_KEY")
            .enqueue(object : Callback<Photos> {
                override fun onFailure(call: Call<Photos>, t: Throwable) {
                    t.printStackTrace()
                    Log.d("TAG", "sosi hui")
                }

                override fun onResponse(
                    call: Call<Photos>,
                    response: Response<Photos>
                ) {
                    if (response.isSuccessful) {
                        for(i in response.body()!!.photos){
                            Log.d("TAG", i.imgSrc)
                        }

                        viewState.showItem(response.body()!!.photos)
                    }

                }

            })
    }
}
