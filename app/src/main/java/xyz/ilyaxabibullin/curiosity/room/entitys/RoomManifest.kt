package xyz.ilyaxabibullin.curiosity.room.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomManifest(
    @PrimaryKey
    @ColumnInfo(name = "sol") var sol: Int?,

    @ColumnInfo(name = "total_photos") var totalPhotos: Long?,
    @ColumnInfo(name = "earth_date") var earthDate: String?
)