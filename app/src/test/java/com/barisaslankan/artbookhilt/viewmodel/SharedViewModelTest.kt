package com.barisaslankan.artbookhilt.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.barisaslankan.artbookhilt.getOrAwaitValueTest
import com.barisaslankan.artbookhilt.repository.FakeArtBookRepository
import com.barisaslankan.artbookhilt.util.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SharedViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SharedViewModel
    @Before
    fun setup(){
        viewModel = SharedViewModel(FakeArtBookRepository())
    }

    @Test
    fun `insert art without year returns error`(){

        viewModel.makeArt("Mona Lisa","Leonardo Da Vinci", "")

        val value = viewModel.insertArtMessage.getOrAwaitValueTest()

        assertThat(value.status).isEqualTo(Status.ERROR)

    }
    @Test
    fun `insert art without name returns error`(){
        viewModel.makeArt("","Da Vinci","1500")

        val value = viewModel.insertArtMessage.getOrAwaitValueTest()

        assertThat(value.status).isEqualTo(Status.ERROR)
    }
    @Test
    fun `insert art without artist returns error`(){
        viewModel.makeArt("Mona Lisa","","1500")

        val value = viewModel.insertArtMessage.getOrAwaitValueTest()

        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}