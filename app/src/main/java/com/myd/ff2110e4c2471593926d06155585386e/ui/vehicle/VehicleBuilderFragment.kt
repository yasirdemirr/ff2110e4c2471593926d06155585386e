package com.myd.ff2110e4c2471593926d06155585386e.ui.vehicle

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myd.ff2110e4c2471593926d06155585386e.R

class VehicleBuilderFragment : Fragment() {

    private lateinit var viewModel: VehicleBuilderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.vehicle_builder_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VehicleBuilderViewModel::class.java)
        // TODO: Use the ViewModel
    }

}