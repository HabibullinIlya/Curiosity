package xyz.ilyaxabibullin.curiosity.entitys

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CuriosityPhoto() :Parcelable{
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.writeString(earthDate)
        dest!!.writeString(imgSrc)
        dest!!.writeInt(id)
        dest!!.writeInt(sol)
        dest!!.writeInt(totalPhotos)
    }

    override fun describeContents(): Int {
        return 0
    }



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

    @SerializedName("total_photos")
    @Expose
    var totalPhotos = 0

    constructor(parcel: Parcel) : this() {
        earthDate = parcel.readString()
        imgSrc = parcel.readString()
        id = parcel.readInt()
        sol = parcel.readInt()
        totalPhotos = parcel.readInt()
    }

    companion object CREATOR : Parcelable.Creator<CuriosityPhoto> {
        override fun createFromParcel(parcel: Parcel): CuriosityPhoto {
            return CuriosityPhoto(parcel)
        }

        override fun newArray(size: Int): Array<CuriosityPhoto?> {
            return arrayOfNulls(size)
        }
    }
}