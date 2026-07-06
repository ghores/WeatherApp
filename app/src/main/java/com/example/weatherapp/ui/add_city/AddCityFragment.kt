package com.example.weatherapp.ui.add_city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.weatherapp.databinding.FragmentAddCityBinding
import com.example.weatherapp.utils.base.BaseBottomSheetFragment
import com.example.weatherapp.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCityFragment : BaseBottomSheetFragment<FragmentAddCityBinding>() {
    override val bindingInflater: (inflater: LayoutInflater) -> FragmentAddCityBinding
        get() = FragmentAddCityBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}