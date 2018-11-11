package xyz.ilyaxabibullin.curiosity.entitys

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ManifestResponce {
    @SerializedName("photo_manifest")
    @Expose
    var manifest = Manifest()
}