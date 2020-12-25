package com.myd.ff2110e4c2471593926d06155585386e.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.myd.ff2110e4c2471593926d06155585386e.databinding.HomePageFragmentBinding
import com.myd.ff2110e4c2471593926d06155585386e.resources.NetworkState
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class HomePageFragment : Fragment() {

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory

    private lateinit var viewModel: HomePageViewModel

    private lateinit var binding: HomePageFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomePageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelProvider).get(HomePageViewModel::class.java)
        binding.viewModel = viewModel
        observableData()
    }

    private fun observableData() {
        viewModel.getStations().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource) {
                    is NetworkState.Loading -> {
                    }

                    is NetworkState.Error -> {
                    }
                    is NetworkState.Success -> {
                        resource.data.let { data -> viewModel.saveStationsToRoom(data) }
                    }
                }
            }
        })
    }

}