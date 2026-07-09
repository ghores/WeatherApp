package com.example.weatherapp.ui.info

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.data.model.info.PollutionModel
import com.example.weatherapp.data.model.info.ResponsePollution
import com.example.weatherapp.databinding.FragmentInfoBinding
import com.example.weatherapp.utils.BASE_URL_IMAGE
import com.example.weatherapp.utils.PNG_IMAGE
import com.example.weatherapp.utils.base.BaseBottomSheetFragment
import com.example.weatherapp.utils.isVisible
import com.example.weatherapp.utils.loadImage
import com.example.weatherapp.utils.network.NetworkRequest
import com.example.weatherapp.utils.setupRecyclerview
import com.example.weatherapp.utils.showSnackBar
import com.example.weatherapp.viewmodel.InfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@AndroidEntryPoint
class InfoFragment : BaseBottomSheetFragment<FragmentInfoBinding>() {
    override val bindingInflater: (inflater: LayoutInflater) -> FragmentInfoBinding
        get() = FragmentInfoBinding::inflate

    @Inject
    lateinit var pollutionAdapter: PollutionAdapter

    //Other
    private val infoViewModel by viewModels<InfoViewModel>()
    private val args by navArgs<InfoFragmentArgs>()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        // InitViews
        binding.apply {
            //Args
            args.Data.let { data ->
                //Call api
                lifecycleScope.launch {
                    delay(500.milliseconds)
                    data.coord?.let { coord ->
                        infoViewModel.callPollutionApi(coord.lat!!, coord.lon!!)
                    }
                }
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
        //Load data
        loadPollutionData()
    }

    private fun loadPollutionData() {
        binding.apply {
            infoViewModel.pollutionData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequest.Loading -> { loading.isVisible(true, pollutionCard) }
                    is NetworkRequest.Success -> {
                        loading.isVisible(false, pollutionCard)
                        response.data?.let { data ->
                            data.list?.let {list ->
                                if (list.isNotEmpty()) {
                                    list[0].let { myData ->
                                        pollutionLay.apply {
                                            //Image
                                            pollutionIconImg.apply {
                                                setImageResource(pollutionIcon(myData.main))
                                                //Tint
                                                imageTintList = ColorStateList.valueOf(
                                                    ContextCompat.getColor(requireContext(), pollutionColors(myData.main))
                                                )
                                            }
                                            //Info
                                            pollutionInfoTxt.apply {
                                                text = pollutionMessage(myData.main)
                                                setTextColor(ContextCompat.getColor(requireContext(), pollutionColors(myData.main)))
                                            }
                                            //Shadow
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                                pollutionCard.apply {
                                                    outlineAmbientShadowColor = ContextCompat.getColor(requireContext(), pollutionColors(myData.main))
                                                    outlineSpotShadowColor = ContextCompat.getColor(requireContext(), pollutionColors(myData.main))
                                                }
                                            }
                                            //Pollution
                                            iniRecyclerView(fillPollutionData(myData.components))
                                        }
                                    }
                                }
                            }
                        }
                    }
                    is NetworkRequest.Error -> {
                        loading.isVisible(false, pollutionCard)
                        root.showSnackBar(response.error!!)
                    }
                }
            }
        }
    }

    private fun pollutionColors(data: ResponsePollution.Data.Main): Int {
        return when (data.aqi) {
            1 -> R.color.green
            2 -> R.color.yellow
            3 -> R.color.orange
            4 -> R.color.red
            5 -> R.color.purple
            else -> 0
        }
    }

    private fun pollutionIcon(data: ResponsePollution.Data.Main): Int {
        return when (data.aqi) {
            1 -> R.drawable.face_smile_hearts
            2, 3 -> R.drawable.face_clouds
            4, 5 -> R.drawable.face_mask
            else -> 0
        }
    }

    private fun pollutionMessage(data: ResponsePollution.Data.Main): String {
        return when (data.aqi) {
            1 -> getString(R.string.messageAQI1)
            2 -> getString(R.string.messageAQI2)
            3, 4 -> getString(R.string.messageAQI3_4)
            5 -> getString(R.string.messageAQI5)
            else -> ""
        }
    }

    private fun fillPollutionData(data: ResponsePollution.Data.Components): MutableList<PollutionModel> {
        val list = mutableListOf<PollutionModel>()
        list.add(PollutionModel(getString(R.string.co), data.co))
        list.add(PollutionModel(getString(R.string.no2), data.no2))
        list.add(PollutionModel(getString(R.string.o3), data.o3))
        list.add(PollutionModel(getString(R.string.so2), data.so2))
        list.add(PollutionModel(getString(R.string.pm2_5), data.pm25))
        list.add(PollutionModel(getString(R.string.pm10), data.pm10))
        return list
    }

    private fun iniRecyclerView(list: List<PollutionModel>) {
        pollutionAdapter.setData(list)
        binding.pollutionLay.pollutionList.setupRecyclerview(LinearLayoutManager(requireContext()), pollutionAdapter)
    }
}