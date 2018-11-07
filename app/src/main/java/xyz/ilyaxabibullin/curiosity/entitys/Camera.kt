package xyz.ilyaxabibullin.curiosity.entitys

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Camera {
    @SerializedName("id")
    @Expose
    val id = 0
    @SerializedName("name")
    @Expose
    var name = ""
    @SerializedName("rover_id")
    @Expose
    var roverId = 0
    @SerializedName("full_name")
    @Expose
    private val fullName = " "
}