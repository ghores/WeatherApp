package com.example.weatherapp.ui.add_city

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.model.add_city.ResponseCitiesList
import com.example.weatherapp.databinding.ItemCitiesBinding
import com.example.weatherapp.utils.base.BaseDiffUtils
import javax.inject.Inject

class AddCitiesAdapter @Inject constructor() : RecyclerView.Adapter<AddCitiesAdapter.ViewHolder>() {

    private var items = emptyList<ResponseCitiesList.ResponseCitiesListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCitiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = position

    override fun getItemId(position: Int): Long = position.toLong()

    inner class ViewHolder(private val binding: ItemCitiesBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ResponseCitiesList.ResponseCitiesListItem) {
            binding.apply {
                //Name
                if (item.localNames?.fa != null) {
                    citiesNameTxt.text = "${item.localNames.fa} - ${item.country}"
                } else {
                    citiesNameTxt.text = "${item.name} - ${item.country}"
                }
                //Click
                root.setOnClickListener {
                    //Click
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((ResponseCitiesList.ResponseCitiesListItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ResponseCitiesList.ResponseCitiesListItem) -> Unit) {
        onItemClickListener = listener
    }

    fun setData(data: List<ResponseCitiesList.ResponseCitiesListItem>) {
        val adapterDiffUtils = BaseDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }
}