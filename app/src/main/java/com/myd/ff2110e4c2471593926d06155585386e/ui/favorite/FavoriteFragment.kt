package com.myd.ff2110e4c2471593926d06155585386e.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.myd.ff2110e4c2471593926d06155585386e.databinding.FavoriteFragmentBinding
import com.myd.ff2110e4c2471593926d06155585386e.ui.favorite.adapter.FavoriteStationsAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory

    private lateinit var viewModel: FavoriteViewModel

    private lateinit var binding: FavoriteFragmentBinding

    private lateinit var adapterFavorite: FavoriteStationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelProvider).get(FavoriteViewModel::class.java)
        binding.viewModel = viewModel
        adapterFavorite = getAdapter()
        initRecyclerView()
        viewModel.getFavoriteStations()
        viewModel.favoriteStationsLiveData.observe(viewLifecycleOwner, {
            adapterFavorite.setFavoriteStations(it)
            adapterFavorite.notifyDataSetChanged()
        })
    }

    private fun initRecyclerView() {
        binding.favoriteList.apply {
            adapter = adapterFavorite
        }
    }

    private fun getAdapter() = FavoriteStationsAdapter {
        viewModel.removeFavoriteItem(it)
    }

}