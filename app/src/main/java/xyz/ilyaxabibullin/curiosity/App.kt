package xyz.ilyaxabibullin.curiosity

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.ilyaxabibullin.curiosity.entitys.CuriosityPhoto
import xyz.ilyaxabibullin.curiosity.network.NASAApi
import xyz.ilyaxabibullin.curiosity.room.AppDatabase
import java.util.concurrent.TimeUnit

class App : Application() {
    companion object {
        lateinit var retrofit: Retrofit
        const val baseUrl = "https://api.nasa.gov"

        const val apiKey = "a1N9NkJYOVTr5CNkIrZVsl6l0odlCeBRxP5lbTAq"

        lateinit var photos: ArrayList<CuriosityPhoto>
        lateinit var nasaApi: NASAApi

        lateinit var db: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        var interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        var client = OkHttpClient.Builder()
            .readTimeout(55, TimeUnit.SECONDS)
            .addInterceptor(interceptor).build()


        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()


        nasaApi = retrofit.create(NASAApi::class.java)

        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "curiosity-db"
        ).build()


    }
}