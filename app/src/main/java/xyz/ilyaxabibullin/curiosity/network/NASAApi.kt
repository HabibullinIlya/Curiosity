package xyz.ilyaxabibullin.curiosity.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import xyz.ilyaxabibullin.curiosity.entitys.CuriosityPhoto
import xyz.ilyaxabibullin.curiosity.entitys.Photos

interface NASAApi {
    @GET("/mars-photos/api/v1/rovers/curiosity/photos?")
    fun getPhotos(@Query("earth_date")date:String,
                  //@Query("page")page:Int,
                  @Query("api_key") key:String):Call<Photos>

}