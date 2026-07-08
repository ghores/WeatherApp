package com.example.weatherapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMainBinding
import com.example.weatherapp.utils.base.BaseFragment
import com.example.weatherapp.utils.onceObserve
import com.example.weatherapp.utils.setStatusBarIconsColor
import com.example.weatherapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

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
        //InitViews
        binding.apply {
            menuImg.setOnClickListener { findNavController().navigate(R.id.actionToCitiesList) }
            addImg.setOnClickListener { findNavController().navigate(R.id.actionToAddCity) }
        }
        //Cities list
        mainViewModel.callCitiesData()
        //Load data
        loadCitiesData()
    }

    private fun loadCitiesData() {
        binding.apply {
            mainViewModel.citiesData.onceObserve(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    emptyLay.isVisible = false
                    container.isVisible = true
                } else {
                    emptyLay.isVisible = true
                    findNavController().navigate(R.id.actionToAddCity)
                }
            }
        }
    }
}