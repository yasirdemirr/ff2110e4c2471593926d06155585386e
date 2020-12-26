package com.myd.ff2110e4c2471593926d06155585386e.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myd.ff2110e4c2471593926d06155585386e.R
import com.myd.ff2110e4c2471593926d06155585386e.constant.Constants.VEHICLE
import com.myd.ff2110e4c2471593926d06155585386e.data.model.VehiclePreferences
import com.myd.ff2110e4c2471593926d06155585386e.databinding.HomePageFragmentBinding
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
        val navigationBar = activity?.findViewById<BottomNavigationView>(R.id.bottomNavi)
        navigationBar?.visibility = View.VISIBLE
        binding = HomePageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelProvider).get(HomePageViewModel::class.java)
        binding.viewModel = viewModel

    }

    override fun onResume() {
        super.onResume()
        arguments?.getParcelable<VehiclePreferences>(VEHICLE)?.let { viewModel.parseIntent(it) }

    }
}