package xyz.ilyaxabibullin.curiosity.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import xyz.ilyaxabibullin.curiosity.entitys.CuriosityPhoto

interface NASAApi {
    @GET("/api/v1/rovers/curiosity/photos")
    fun getPhotos(@Query("earth_date")date:String,
                  @Query("page")page:Int,
                  @Query("key") key:String):Call<ArrayList<CuriosityPhoto>>

}