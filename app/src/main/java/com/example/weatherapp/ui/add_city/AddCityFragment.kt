package com.example.weatherapp.ui.add_city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.data.model.add_city.ResponseCitiesList
import com.example.weatherapp.databinding.FragmentAddCityBinding
import com.example.weatherapp.utils.base.BaseBottomSheetFragment
import com.example.weatherapp.utils.network.NetworkRequest
import com.example.weatherapp.utils.setupRecyclerview
import com.example.weatherapp.utils.showSnackBar
import com.example.weatherapp.viewmodel.AddCitiesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddCityFragment : BaseBottomSheetFragment<FragmentAddCityBinding>() {
    override val bindingInflater: (inflater: LayoutInflater) -> FragmentAddCityBinding
        get() = FragmentAddCityBinding::inflate

    @Inject
    lateinit var citiesAdapter: CitiesAdapter

    //other
    private val addCitiesViewModel by viewModels<AddCitiesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            searchInpLay.setEndIconOnClickListener {
                val search = searchEdt.text.toString()
                if (isNetworkAvailable) {
                    if (search.isNotEmpty()) {
                        addCitiesViewModel.callCitiesApi(search)
                    } else {
                        root.showSnackBar(getString(R.string.searchCanNotBeEmpty))
                    }
                }
            }
        }
        //Load data
        loadSearchCityData()
    }

    private fun loadSearchCityData() {
        binding.apply {
            addCitiesViewModel.citiesData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequest.Loading -> { loading.isVisible = true }
                    is NetworkRequest.Success -> {
                        loading.isVisible = false
                        response.data?.let { data ->
                            if (data.isNotEmpty()) {
                                initRecyclerView(data)
                            }
                        }
                    }
                    is NetworkRequest.Error -> {
                        loading.isVisible = false
                        root.showSnackBar(response.error!!)
                    }
                }
            }
        }
    }

    private fun initRecyclerView(list: List<ResponseCitiesList.ResponseCitiesListItem>) {
        citiesAdapter.setData(list)
        binding.citiesList.setupRecyclerview(LinearLayoutManager(requireContext()), citiesAdapter)
    }
}