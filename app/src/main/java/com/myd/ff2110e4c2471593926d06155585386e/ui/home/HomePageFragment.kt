package com.myd.ff2110e4c2471593926d06155585386e.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myd.ff2110e4c2471593926d06155585386e.R
import com.myd.ff2110e4c2471593926d06155585386e.constant.Constants.VEHICLE
import com.myd.ff2110e4c2471593926d06155585386e.data.model.VehiclePreferences
import com.myd.ff2110e4c2471593926d06155585386e.databinding.HomePageFragmentBinding
import com.myd.ff2110e4c2471593926d06155585386e.ui.home.adapter.StationsAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class HomePageFragment : Fragment() {

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory

    private lateinit var viewModel: HomePageViewModel

    private lateinit var binding: HomePageFragmentBinding

    private lateinit var stationAdapter: StationsAdapter

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
        stationAdapter = getAdapter()
        initRecyclerView()
        viewModel =
            ViewModelProvider(this, viewModelProvider).get(HomePageViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.getListOfStation()
        binding.lifecycleOwner = this
        arguments?.getParcelable<VehiclePreferences>(VEHICLE)?.let { viewModel.parseIntent(it) }

        viewModel.run {
            stationLiveData.observe(viewLifecycleOwner, { result ->
                result?.let {
                    stationAdapter.setStations(it)
                    stationAdapter.notifyDataSetChanged()
                }
            })
            selectedStationFavoriteLiveData.observe(viewLifecycleOwner, {
                it?.let {
                    it.id?.let { id -> stationAdapter.notifyItemChanged(id.minus(1)) }
                }
            })
            goSelectedStationLiveData.observe(viewLifecycleOwner, {
                it?.let {
                    it.id?.let { id ->
                        binding.spaceList.smoothScrollToPosition(
                            id.minus(
                                1
                            )
                        )
                    }
                }
            })
            missionCompletedLiveData.observe(viewLifecycleOwner, {
                createAlert(it)
            })
        }

    }

    private fun goWorldPosition() {
        binding.spaceList.smoothScrollToPosition(0)
    }

    private fun initRecyclerView() {
        binding.spaceList.apply {
            adapter = stationAdapter
        }
    }

    private fun getAdapter() = StationsAdapter { station, clickType ->
        station?.let { station -> viewModel.travelBySelectedStation(station, clickType) }
    }

    private fun createAlert(message: String) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setMessage(message)
        alertDialog.setTitle("Bilgilendirme")
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton("Tamam") { dialog, _ ->
            goWorldPosition()
            dialog.cancel()
        }
        alertDialog.show()
    }


    override fun onResume() {
        super.onResume()
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    query.let { viewModel.getFindItemByQuery(it.trim()) }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?) = true
        }
        )
    }

    override fun onPause() {
        super.onPause()
        binding.search.setQuery("", false)
        binding.search.clearFocus()
        viewModel.removeSearchData()
    }
}