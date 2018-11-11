package xyz.ilyaxabibullin.curiosity.entitys

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Manifest {
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
    var maxSol = -1

    @SerializedName("max_date")
    @Expose
    var maxDate = ""

    @SerializedName("total_photos")
    @Expose
    var totalPhotos = -1

    @SerializedName("photos")
    @Expose
    var photos = ArrayList<CuriosityPhoto>()

}