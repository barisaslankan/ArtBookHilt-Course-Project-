package com.barisaslankan.artbookhilt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barisaslankan.artbookhilt.model.Art
import com.barisaslankan.artbookhilt.model.ImageResponse
import com.barisaslankan.artbookhilt.repository.ArtBookRepositoryInterface
import com.barisaslankan.artbookhilt.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository : ArtBookRepositoryInterface): ViewModel()
{

    val artList = repository.getArt()

    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList : LiveData<Resource<ImageResponse>> get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl : LiveData<String> get() = selectedImage

    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage : LiveData<Resource<Art>> get() = insertArtMsg

    fun resetArtMessage(){
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }
    fun setSelectedImage(url : String){
        selectedImage.postValue(url)
    }
    fun deleteArt(art:Art) = viewModelScope.launch {
        repository.deleteArt(art)
    }
    fun insertArt(art: Art) = viewModelScope.launch {
        repository.insertArt(art)
    }

    fun makeArt(name : String, artistName : String, year : String) {
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty() ) {
            insertArtMsg.postValue(Resource.error("Please fill in all fields", null))
            return
        }
        val yearInt = try {
            year.toInt()
        } catch (e: Exception) {
            insertArtMsg.postValue(Resource.error("Year should be number",null))
            return
        }

        val art = Art(name, artistName, yearInt,selectedImage.value?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }

    fun searchForImage (searchString : String) {

        if(searchString.isEmpty()) {
            return
        }
        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.imageSearch(searchString)
            images.value = response
        }
    }

}