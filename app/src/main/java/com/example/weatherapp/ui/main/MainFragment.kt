package com.example.weatherapp.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMainBinding
import com.example.weatherapp.utils.base.BaseFragment
import com.example.weatherapp.utils.events.EventBus
import com.example.weatherapp.utils.events.Events
import com.example.weatherapp.utils.isVisible
import com.example.weatherapp.utils.network.NetworkRequest
import com.example.weatherapp.utils.onceObserve
import com.example.weatherapp.utils.setStatusBarIconsColor
import com.example.weatherapp.utils.showSnackBar
import com.example.weatherapp.viewmodel.MainViewModel
import com.github.matteobattilana.weather.PrecipType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {
    override val bindingInflater: (inflater: LayoutInflater) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    //Other
    private val mainViewModel by viewModels<MainViewModel>()
    private val calendar by lazy { Calendar.getInstance() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Change statusBar color
        requireActivity().setStatusBarIconsColor(false)
        //InitViews
        binding.apply {
            menuImg.setOnClickListener { findNavController().navigate(R.id.actionToCitiesList) }
            addImg.setOnClickListener { findNavController().navigate(R.id.actionToAddCity) }
        }
        //Cities list
        mainViewModel.callCitiesData()
        //Load data
        loadCitiesData()
        loadCurrentWeatherData()
        //Event
        lifecycleScope.launch {
            EventBus.subscribe<Events.OnUpdateWeather> {
                mainViewModel.callCurrentWeatherApi(it.lat!!, it.lon!!)
            }
        }
    }

    private fun loadCitiesData() {
        binding.apply {
            mainViewModel.citiesData.onceObserve(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    emptyLay.isVisible = false
                    mainViewModel.callCurrentWeatherApi(it[0].lat!!, it[0].lon!!)
                } else {
                    emptyLay.isVisible = true
                    findNavController().navigate(R.id.actionToAddCity)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadCurrentWeatherData() {
        binding.apply {
            mainViewModel.currentWeatherData.observe(viewLifecycleOwner) {response->
                when(response) {
                    is NetworkRequest.Loading -> { loading.isVisible(true, container) }
                    is NetworkRequest.Success -> {
                        loading.isVisible(false, container)
                        response.data?.let {data->
                            //Name
                            cityName.text = data.name
                            //Weather
                            data.weather?.let {weather->
                                if (weather.isNotEmpty()) {
                                    weather[0]?.let {
                                        //Info
                                        infoTxt.text = it.description
                                        //Bg
                                        bgImg.load(
                                            if (isNightNow()) R.drawable.bg_night else it.icon?.let { icon-> setDynamicallyWallpaper(icon) }
                                        ) {
                                            crossfade(true)
                                            crossfade(100)
                                        }
                                    }
                                }
                            }
                            //Main
                            data.main?.let { main ->
                                tempTxt.text = "${main.temp}${getString(R.string.degreeCelsius)}"
                                TempInfoTxt.text = "${main.tempMin}${getString(R.string.degree)}    " +
                                                   "${main.tempMax}${getString(R.string.degree)}"
                            }
                        }
                    }
                    is NetworkRequest.Error -> {
                        loading.isVisible(false, container)
                        root.showSnackBar(response.error!!)
                    }
                }
            }
        }
    }

    private fun isNightNow(): Boolean {
        return calendar.get(Calendar.HOUR_OF_DAY) >= 18
    }

    private fun setDynamicallyWallpaper(icon: String): Int {
        return when (icon.dropLast(1)) {
            "01" -> {
                initWeatherView(PrecipType.CLEAR)
                R.drawable.bg_sun
            }

            "02", "03", "04" -> {
                initWeatherView(PrecipType.CLEAR)
                R.drawable.bg_cloud
            }

            "09", "10", "11" -> {
                initWeatherView(PrecipType.RAIN)
                R.drawable.bg_rain
            }

            "13" -> {
                initWeatherView(PrecipType.SNOW)
                R.drawable.bg_snow
            }

            "50" -> {
                initWeatherView(PrecipType.CLEAR)
                R.drawable.bg_haze
            }

            else -> 0
        }
    }

    private fun initWeatherView(type: PrecipType) {
        binding.weatherView.apply {
            setWeatherData(type)
            angle = 20
            emissionRate = 100.0f
            speed = 50
        }
    }
}