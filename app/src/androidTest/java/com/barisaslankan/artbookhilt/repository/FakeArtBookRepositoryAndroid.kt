package com.barisaslankan.artbookhilt.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.barisaslankan.artbookhilt.model.Art
import com.barisaslankan.artbookhilt.model.ImageResponse
import com.barisaslankan.artbookhilt.util.Resource

class FakeArtBookRepositoryAndroid : ArtBookRepositoryInterface {

    private val arts = mutableListOf<Art>()
    private val artLiveData = MutableLiveData<List<Art>>(arts)
    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refreshArtData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refreshArtData()
    }

    override fun getArt(): LiveData<List<Art>> {
        return artLiveData
    }

    override suspend fun imageSearch(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(),0,0))
    }

    private fun refreshArtData(){
        artLiveData.postValue(arts)
    }
}