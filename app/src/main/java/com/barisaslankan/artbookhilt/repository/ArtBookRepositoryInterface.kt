package com.barisaslankan.artbookhilt.repository

import androidx.lifecycle.LiveData
import com.barisaslankan.artbookhilt.model.Art
import com.barisaslankan.artbookhilt.model.ImageResponse
import com.barisaslankan.artbookhilt.util.Resource

interface ArtBookRepositoryInterface {

    suspend fun insertArt(art : Art)

    suspend fun deleteArt(art: Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun imageSearch(imageString: String) : Resource<ImageResponse>

}