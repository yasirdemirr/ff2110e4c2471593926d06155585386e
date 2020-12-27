package com.myd.ff2110e4c2471593926d06155585386e.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.myd.ff2110e4c2471593926d06155585386e.R
import com.myd.ff2110e4c2471593926d06155585386e.constant.ClickType
import com.myd.ff2110e4c2471593926d06155585386e.data.model.Station
import com.myd.ff2110e4c2471593926d06155585386e.databinding.StationItemBinding

class StationsAdapter(
    val onItemClick: (Station?, ClickType) -> Unit?
) : RecyclerView.Adapter<StationsAdapter.StationsItemViewHolder>(
) {

    private var stationList: MutableList<Station> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StationsItemViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.station_item,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: StationsItemViewHolder, position: Int) {
        holder.bind(getStation(position))
    }

    override fun getItemCount(): Int = stationList.size

    private fun getStation(position: Int) = stationList[position]

    fun setStations(stations: List<Station>) {
        stationList.clear()
        val beforeSize = stations.size
        stationList.addAll(stations)
        notifyItemRangeInserted(beforeSize, stations.size)
    }

    inner class StationsItemViewHolder(private val binding: StationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Station?) {
            binding.run {
                data = item
                executePendingBindings()
                btnTravel.setOnClickListener {
                    onItemClick(item, ClickType.BUTTON)
                }
                binding.btnTravel.visibility =
                    if (data?.capacity != data?.stock && data?.isCurrentLocation == false) View.VISIBLE else View.INVISIBLE
                favoriteIcon.setOnClickListener {
                    onItemClick(item, ClickType.ICON)
                }
                binding.capacity.text = data?.stock.toString() + "/" + data?.capacity.toString()
            }
        }
    }
}
