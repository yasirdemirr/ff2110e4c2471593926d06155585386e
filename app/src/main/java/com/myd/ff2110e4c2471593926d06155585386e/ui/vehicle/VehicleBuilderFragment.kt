package com.myd.ff2110e4c2471593926d06155585386e.ui.vehicle

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myd.ff2110e4c2471593926d06155585386e.R
import com.myd.ff2110e4c2471593926d06155585386e.constant.Constants.VEHICLE
import com.myd.ff2110e4c2471593926d06155585386e.databinding.VehicleBuilderFragmentBinding
import com.myd.ff2110e4c2471593926d06155585386e.resources.NetworkState
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class VehicleBuilderFragment : Fragment(), SeekBar.OnSeekBarChangeListener, TextWatcher {

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory

    private lateinit var viewModel: VehicleBuilderViewModel

    private lateinit var binding: VehicleBuilderFragmentBinding

    private var firsTime = true
    private var progressValue: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navigationBar = activity?.findViewById<BottomNavigationView>(R.id.bottomNavi)
        navigationBar?.visibility = View.GONE
        binding = VehicleBuilderFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelProvider).get(VehicleBuilderViewModel::class.java)
        binding.viewModel = viewModel
        observableData()
        binding.btnContinue.setOnClickListener {
            if (viewModel.checkButtonEnableValue()) {
                val bundle = bundleOf(VEHICLE to viewModel.vehicle)
                findNavController().navigate(
                    R.id.action_vehicleBuilderFragment_to_homePageFragment,
                    bundle
                )
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Bütün Alanları Doldurunuz.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        seekBarSetOnClickListener()
        binding.vehicleNameInput.addTextChangedListener(this)

    }

    private fun seekBarSetOnClickListener() {
        binding.capacityBar.setOnSeekBarChangeListener(this)
        binding.durabilityBar.setOnSeekBarChangeListener(this)
        binding.speedBar.setOnSeekBarChangeListener(this)
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

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser && firsTime) {
            seekBar?.let {
                when (it.id) {
                    R.id.capacityBar -> {
                        firsTime = false
                        progressValue = viewModel.checkVehicleCapacity(progress)
                    }
                    R.id.speedBar -> {
                        firsTime = false
                        progressValue = viewModel.checkVehicleSpeed(progress)
                    }
                    R.id.durabilityBar -> {
                        firsTime = false
                        progressValue = viewModel.checkVehicleDurability(progress)
                    }
                }
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        seekBar?.progress
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        firsTime = true
        seekBar?.progress = progressValue
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        viewModel.getVehicleName(s)
    }

    override fun afterTextChanged(s: Editable?) {
    }

}