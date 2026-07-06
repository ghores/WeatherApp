package com.example.weatherapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.weatherapp.databinding.FragmentMainBinding
import com.example.weatherapp.utils.base.BaseFragment
import com.example.weatherapp.utils.setStatusBarIconsColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: BaseFragment<FragmentMainBinding>() {
    override val bindingInflater: (inflater: LayoutInflater) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Change statusBar color
        requireActivity().setStatusBarIconsColor(false)
    }
}