package com.barisaslankan.artbookhilt.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.barisaslankan.artbookhilt.model.Art

@Dao
interface ArtDao {
    @Query("SELECT * FROM arts")
    fun observeArts() : LiveData<List<Art>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(art:Art)

    @Delete
    suspend fun deleteArt(art:Art)

}