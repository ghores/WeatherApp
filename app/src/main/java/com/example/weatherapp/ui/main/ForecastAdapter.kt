package com.example.weatherapp.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weatherapp.R
import com.example.weatherapp.data.model.main.ResponseForecast
import com.example.weatherapp.databinding.ItemForecastBinding
import com.example.weatherapp.utils.BASE_URL_IMAGE
import com.example.weatherapp.utils.PNG_IMAGE
import com.example.weatherapp.utils.base.BaseDiffUtils
import com.example.weatherapp.utils.convertToDayName
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ForecastAdapter @Inject constructor(@param:ApplicationContext private val context: Context) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    private var items = emptyList<ResponseForecast.Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = position

    override fun getItemId(position: Int): Long = position.toLong()

    inner class ViewHolder(private val binding: ItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ResponseForecast.Data) {
            binding.apply {
                //Date
                item.dtTxt?.let { date ->
                    val dayName = date.split(" ")[0].convertToDayName()
                    val hour = date.split(" ")[1].dropLast(3)
                    dateTxt.text = "$dayName\n$hour"
                }
                //Icon
                val icon = item.weather?.get(0)?.icon
                val iconUrl = "$BASE_URL_IMAGE$icon$PNG_IMAGE"
                iconImg.load(iconUrl)
                //Temp
                item.main?.let {main->
                    tempTxt.text = "${main.temp}${context.getString(R.string.degree)}"
                    humidityTxt.text = "${main.humidity}"
                }
            }
        }
    }

    fun setData(data: List<ResponseForecast.Data>) {
        val adapterDiffUtils = BaseDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }
}