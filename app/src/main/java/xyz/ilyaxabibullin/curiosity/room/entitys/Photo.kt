package xyz.ilyaxabibullin.curiosity.room.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Photo (
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "earth_date") var earthDate: String?,
    @ColumnInfo(name = "img_src") var imgSrc: String,
    //наверное херню морожу, но надеюсь так хотя бы как-то работать будет)
    @ColumnInfo(name = "page") var page:Int?

)