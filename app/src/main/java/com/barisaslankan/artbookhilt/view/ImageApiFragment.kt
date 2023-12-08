package com.barisaslankan.artbookhilt.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.barisaslankan.artbookhilt.R
import com.barisaslankan.artbookhilt.adapter.ImageRecyclerAdapter
import com.barisaslankan.artbookhilt.databinding.FragmentImageApiBinding
import com.barisaslankan.artbookhilt.util.Status
import com.barisaslankan.artbookhilt.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ImageApiFragment @Inject constructor(
     val adapter : ImageRecyclerAdapter
): Fragment(R.layout.fragment_image_api) {

    private var fragmentImageApiBinding : FragmentImageApiBinding? = null

     lateinit var viewModel : SharedViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentImageApiBinding.bind(view)
        fragmentImageApiBinding = binding

        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        var job: Job? = null

        binding.imageApiSearchEditText.addTextChangedListener {

            job?.cancel()

            job = lifecycleScope.launch {
                delay(1000)

                it?.let{
                    if(it.toString().isNotEmpty()){
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }

        subscribeToObservers()

        binding.imageApiRecyclerView.adapter = adapter
        binding.imageApiRecyclerView.layoutManager = GridLayoutManager(requireContext(),3)

        adapter.setOnImageClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }

    }

    private fun subscribeToObservers(){

        viewModel.imageList.observe(viewLifecycleOwner, Observer{

            when(it.status){
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map {imageResult ->
                        imageResult.previewURL
                    }
                    adapter.images = urls ?: listOf()
                    fragmentImageApiBinding?.fragmentImageApiProgressBar?.visibility = View.GONE
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()
                    fragmentImageApiBinding?.fragmentImageApiProgressBar?.visibility = View.GONE

                }

                Status.LOADING -> {
                    fragmentImageApiBinding?.fragmentImageApiProgressBar?.visibility = View.VISIBLE

                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentImageApiBinding = null
    }

}