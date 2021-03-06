package xyz.ilyaxabibullin.curiosity

import android.app.Application
import android.widget.Toast
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.ilyaxabibullin.curiosity.entitys.CuriosityPhoto
import xyz.ilyaxabibullin.curiosity.entitys.Manifest
import xyz.ilyaxabibullin.curiosity.network.NASAApi
import java.util.concurrent.TimeUnit

class App :Application(){
    companion object {
        lateinit var retrofit: Retrofit
        const val baseUrl = "https://api.nasa.gov"

        const val apiKey = "a1N9NkJYOVTr5CNkIrZVsl6l0odlCeBRxP5lbTAq"

        lateinit var photos:ArrayList<CuriosityPhoto>
        lateinit var nasaApi: NASAApi
    }

    override fun onCreate() {
        super.onCreate()

        var interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        var client = OkHttpClient.Builder()
            .readTimeout(55,TimeUnit.SECONDS)
            .addInterceptor(interceptor).build()


        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()


        nasaApi = retrofit.create(NASAApi::class.java)


    }
}