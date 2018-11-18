package xyz.ilyaxabibullin.curiosity.room

import androidx.room.Database
import androidx.room.RoomDatabase

import xyz.ilyaxabibullin.curiosity.room.entitys.RoomManifest
import xyz.ilyaxabibullin.curiosity.room.entitys.Photo


@Database(entities = [RoomManifest::class, Photo::class],version = 2)
abstract class AppDatabase:RoomDatabase() {
    abstract fun getRoomDao(): RoomDao
}