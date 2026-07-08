package com.example.weatherapp.ui.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.data.database.CitiesEntity
import com.example.weatherapp.databinding.FragmentCitiesListBinding
import com.example.weatherapp.utils.base.BaseBottomSheetFragment
import com.example.weatherapp.utils.events.EventBus
import com.example.weatherapp.utils.events.Events
import com.example.weatherapp.utils.onceObserve
import com.example.weatherapp.utils.other.CityClickTypes
import com.example.weatherapp.utils.setupRecyclerview
import com.example.weatherapp.viewmodel.CitiesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CitiesListFragment : BaseBottomSheetFragment<FragmentCitiesListBinding>() {
    override val bindingInflater: (inflater: LayoutInflater) -> FragmentCitiesListBinding
        get() = FragmentCitiesListBinding::inflate

    @Inject
    lateinit var citiesAdapter: CitiesAdapter

    //Other
    private val citiesViewModel by viewModels<CitiesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Cities list
        citiesViewModel.callCitiesData()
        //Load data
        loadCitiesData()
    }

    private fun loadCitiesData() {
        binding.apply {
            citiesViewModel.citiesData.onceObserve(viewLifecycleOwner) {cities->
                //Visibility
                visibilityView(cities.isEmpty())
                //Fill recyclerView
                if (cities.isNotEmpty()) {
                    initRecyclerView(cities)
                }
            }
        }
    }

    private fun visibilityView(isEmpty: Boolean) {
        binding.apply {
            emptyLay.isVisible = isEmpty
            containerGroup.isVisible = isEmpty.not()
        }
    }

    private fun initRecyclerView(cities: List<CitiesEntity>) {
        citiesAdapter.setData(cities)
        binding.citiesList.setupRecyclerview(LinearLayoutManager(requireContext()), citiesAdapter)
        //Click
        citiesAdapter.setOnItemClickListener { entity, clickTypes ->
            if (clickTypes == CityClickTypes.SELECT) {
                //Update event
                lifecycleScope.launch {
                    EventBus.publish(Events.OnUpdateWeather(entity.name, entity.lat, entity.lon))
                }
                this@CitiesListFragment.dismiss()
            } else {
                citiesViewModel.deleteCity(entity)
            }
        }
    }
}