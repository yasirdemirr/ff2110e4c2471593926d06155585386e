package com.myd.ff2110e4c2471593926d06155585386e.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.myd.ff2110e4c2471593926d06155585386e.R
import com.myd.ff2110e4c2471593926d06155585386e.data.model.Station
import com.myd.ff2110e4c2471593926d06155585386e.databinding.FavoriteItemBinding

class FavoriteStationsAdapter(
    val onItemClick: (Station?) -> Unit?
) : RecyclerView.Adapter<FavoriteStationsAdapter.FavoriteStationsViewHolder>(
) {

    private var favoriteStations: MutableList<Station> = mutableListOf()


    override fun onBindViewHolder(holder: FavoriteStationsViewHolder, position: Int) {
        holder.bind(getStation(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FavoriteStationsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.favorite_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = favoriteStations.size

    private fun getStation(position: Int) = favoriteStations[position]

    fun setFavoriteStations(stations: List<Station>) {
        favoriteStations.clear()
        val beforeSize = stations.size
        favoriteStations.addAll(stations)
        notifyItemRangeInserted(beforeSize, stations.size)
    }

    inner class FavoriteStationsViewHolder(private val binding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Station?) {
            binding.run {
                data = item
                executePendingBindings()
                favoriteIcon.setOnClickListener {
                    onItemClick(item)
                }
            }
        }
    }

}