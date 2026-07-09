package com.example.weatherapp.ui.info

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentInfoBinding
import com.example.weatherapp.utils.BASE_URL_IMAGE
import com.example.weatherapp.utils.PNG_IMAGE
import com.example.weatherapp.utils.base.BaseBottomSheetFragment
import com.example.weatherapp.utils.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : BaseBottomSheetFragment<FragmentInfoBinding>() {
    override val bindingInflater: (inflater: LayoutInflater) -> FragmentInfoBinding
        get() = FragmentInfoBinding::inflate

    //Other
    private val args by navArgs<InfoFragmentArgs>()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        // InitViews
        binding.apply {
            //Args
            args.Data.let { data ->
                //Weather
                data.weather?.let { weather ->
                    if (weather.isNotEmpty()) {
                        weather[0]?.let {
                            //Info
                            infoTxt.text = it.description
                            //Image
                            val image = "$BASE_URL_IMAGE${it.icon}$PNG_IMAGE"
                            iconImg.loadImage(image)
                        }
                    }
                }
                //Main
                data.main?.let { main ->
                    tempTxt.text = "${main.temp}${getString(R.string.degreeCelsius)}"
                    TempInfoTxt.text = "${main.tempMin}${getString(R.string.degree)}    " +
                            "${main.tempMax}${getString(R.string.degree)}"
                    //Include
                    weatherLay.apply {
                        feelCountTxt.text = "${main.feelsLike}${getString(R.string.degreeCelsius)}"
                        pressureCountTxt.text = "${main.humidity}%"
                    }
                    //Wind
                    data.wind?.let { wind ->
                        weatherLay.windCountTxt.text = "${wind.speed} ${getString(R.string.km_s)}"
                    }
                }
            }
        }
    }
}