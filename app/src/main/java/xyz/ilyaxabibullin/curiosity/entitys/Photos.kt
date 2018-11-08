package xyz.ilyaxabibullin.curiosity.entitys

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Photos {
    @SerializedName("photos")
    @Expose
    var photos = ArrayList<CuriosityPhoto>()
}