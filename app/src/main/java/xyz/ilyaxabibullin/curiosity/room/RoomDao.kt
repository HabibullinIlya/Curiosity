package xyz.ilyaxabibullin.curiosity.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import xyz.ilyaxabibullin.curiosity.room.entitys.RoomManifest
import xyz.ilyaxabibullin.curiosity.room.entitys.Photo

@Dao
interface RoomDao{

    @Query("SELECT * from RoomManifest")
    fun getAllManifests():List<RoomManifest>

    @Query("SELECT * from Photo where earth_date like :date and page like :page")
    fun findeByDateAndPage(date:String, page:Int):List<Photo>

    @Insert
    fun insertAllManifests(vararg roomManifest: RoomManifest)

    @Insert
    fun insertAllPhotos(vararg photo: Photo)


    @Delete
    fun deletePhoto(photo: Photo)

    @Delete
    fun deleteManifest(roomManifest: RoomManifest)

}