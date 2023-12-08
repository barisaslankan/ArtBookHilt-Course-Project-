package com.barisaslankan.artbookhilt.repository

import androidx.lifecycle.LiveData
import com.barisaslankan.artbookhilt.api.RetrofitAPI
import com.barisaslankan.artbookhilt.model.Art
import com.barisaslankan.artbookhilt.model.ImageResponse
import com.barisaslankan.artbookhilt.roomdb.ArtDao
import com.barisaslankan.artbookhilt.util.Resource
import javax.inject.Inject

class ArtBookRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitAPI: RetrofitAPI) : ArtBookRepositoryInterface {
    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun imageSearch(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitAPI.imageSearch(imageString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            } else {
                Resource.error("Error",null)
            }
        } catch (e: Exception) {
            Resource.error("No data!",null)
        }
    }

}