package com.barisaslankan.artbookhilt.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.barisaslankan.artbookhilt.R
import com.barisaslankan.artbookhilt.databinding.FragmentArtDetailsBinding
import com.barisaslankan.artbookhilt.util.Status
import com.barisaslankan.artbookhilt.viewmodel.SharedViewModel
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtDetailsFragment @Inject constructor(
    private val glide : RequestManager
) : Fragment(R.layout.fragment_art_details) {

    private var fragmentArtDetailsBinding : FragmentArtDetailsBinding? = null
    lateinit var viewModel : SharedViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArtDetailsBinding.bind(view)
        fragmentArtDetailsBinding = binding

        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        subscribeToObservers()

        binding.artDetailsImage.setOnClickListener(View.OnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragment2ToImageApiFragment())
        })

        val callback = object : OnBackPressedCallback(enabled = true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.artDetailsSaveButton.setOnClickListener {
            viewModel.makeArt(binding.artDetailsArtNameEditText.text.toString(),
                binding.artDetailsArtistNameEditText.text.toString(),
                binding.artDetailsYearEditText.text.toString())
        }

    }

    private fun subscribeToObservers(){

        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer {url ->

            fragmentArtDetailsBinding?.let {
                glide.load(url).into(it.artDetailsImage)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    Toast.makeText(requireActivity(),"Success", Toast.LENGTH_LONG).show()
                    findNavController().navigateUp()
                    viewModel.resetArtMessage()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {

                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentArtDetailsBinding = null
    }

}