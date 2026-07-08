package com.example.weatherapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMainBinding
import com.example.weatherapp.utils.base.BaseFragment
import com.example.weatherapp.utils.events.EventBus
import com.example.weatherapp.utils.events.Events
import com.example.weatherapp.utils.onceObserve
import com.example.weatherapp.utils.setStatusBarIconsColor
import com.example.weatherapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {
    override val bindingInflater: (inflater: LayoutInflater) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    //Other
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Change statusBar color
        requireActivity().setStatusBarIconsColor(false)
        //Cities list
        mainViewModel.callCitiesData()
        //Load data
        loadCitiesData()

        lifecycleScope.launch {
            EventBus.subscribe<Events.OnUpdateWeather> {
                Toast.makeText(requireContext(), "${it.lat} -- ${it.lon}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadCitiesData() {
        binding.apply {
            mainViewModel.citiesData.onceObserve(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    emptyLay.isVisible = false
                } else {
                    emptyLay.isVisible = true
                    findNavController().navigate(R.id.actionToAddCity)
                }
            }
        }
    }
}