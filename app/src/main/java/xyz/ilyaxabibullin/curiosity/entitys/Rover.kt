package xyz.ilyaxabibullin.curiosity.entitys

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Rover{
    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("name")
    @Expose
    var name = ""


    @SerializedName("landing_date")
    @Expose
    var landingDate = ""

    @SerializedName("launch_date")
    @Expose
    var launchDate = ""

    @SerializedName("status")
    @Expose
    var status = ""

    @SerializedName("max_sol")
    @Expose
    var maxSol = ""

    @SerializedName("max_date")
    @Expose
    var masDate = ""

    @SerializedName("total_photos")
    @Expose
    var totalPhotos = 0

    @SerializedName("cameras")
    @Expose
    var cameras = ArrayList<Camera>()

}