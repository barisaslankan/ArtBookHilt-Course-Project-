package com.barisaslankan.artbookhilt.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.barisaslankan.artbookhilt.R
import com.barisaslankan.artbookhilt.adapter.ArtRecyclerAdapter
import com.barisaslankan.artbookhilt.databinding.FragmentArtsBinding
import com.barisaslankan.artbookhilt.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ArtsFragment @Inject constructor(
    val adapter : ArtRecyclerAdapter
) : Fragment(R.layout.fragment_arts) {

    private var fragmentArtsBinding : FragmentArtsBinding? = null

    private lateinit var viewModel : SharedViewModel

    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedArt = adapter.arts[layoutPosition]
            viewModel.deleteArt(selectedArt)

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        val binding = FragmentArtsBinding.bind(view)
        fragmentArtsBinding = binding

        subscribeToObservers()

        binding.fragmentArtsRecyclerView.adapter = adapter
        binding.fragmentArtsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.fragmentArtsRecyclerView)

        binding.fragmentArtsFab.setOnClickListener(){
            findNavController().navigate(ArtsFragmentDirections.actionArtsFragmentToArtDetailsFragment2())
        }

    }

    private fun subscribeToObservers(){
        viewModel.artList.observe(viewLifecycleOwner, Observer {
            adapter.arts = it
        })
    }

    override fun onDestroyView() {
        fragmentArtsBinding = null
        super.onDestroyView()
    }

}