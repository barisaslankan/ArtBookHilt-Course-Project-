package com.barisaslankan.artbookhilt.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.barisaslankan.artbookhilt.adapter.ArtRecyclerAdapter
import com.barisaslankan.artbookhilt.adapter.ImageRecyclerAdapter
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val glide : RequestManager,
    private val imageRecyclerAdapter: ImageRecyclerAdapter,
    private val artRecyclerAdapter: ArtRecyclerAdapter
) : FragmentFactory() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ImageApiFragment::class.java.name -> ImageApiFragment(imageRecyclerAdapter)
            ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glide)
            ArtsFragment::class.java.name -> ArtsFragment(artRecyclerAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}