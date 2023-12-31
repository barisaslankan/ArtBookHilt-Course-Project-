package com.barisaslankan.artbookhilt.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arts")
data class Art(
    var name : String,
    var artist : String,
    var year : Int,
    var imageUrl : String,
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null
)