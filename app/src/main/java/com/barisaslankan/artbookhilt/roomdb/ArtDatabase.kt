package com.barisaslankan.artbookhilt.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.barisaslankan.artbookhilt.model.Art

@Database(entities = [Art::class], version = 1, exportSchema = false)
abstract class ArtDatabase : RoomDatabase() {
    abstract fun artDao() : ArtDao
}