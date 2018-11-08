package xyz.ilyaxabibullin.curiosity.entitys

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CuriosityPhoto{
    constructor(_earthDate:String, _imgSrc: String)

    @SerializedName("earth_date")
    @Expose
    var earthDate: String = ""

    @SerializedName("img_src")
    @Expose
    var imgSrc: String = ""

    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("sol")
    @Expose
    var sol = 0

    @SerializedName("camera")
    @Expose
    var camera = Camera()
}