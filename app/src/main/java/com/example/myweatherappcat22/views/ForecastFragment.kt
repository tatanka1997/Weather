package com.example.myweatherappcat22.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myweatherappcat22.R
import com.example.myweatherappcat22.adapter.Listener
import com.example.myweatherappcat22.adapter.WeatherAdapter
import com.example.myweatherappcat22.databinding.FragmentForecastBinding
import com.example.myweatherappcat22.model.Main
import com.example.myweatherappcat22.viewmodel.ResultState


class ForecastFragment : BaseFragment(), Listener {

    private var cityName: String = "Atlanta"

    private val binding by lazy {
        FragmentForecastBinding.inflate(layoutInflater)
    }

    private val weatherAdapter by lazy {
        WeatherAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
          cityName = it.getString("city")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding.forecastRV.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )

            adapter = weatherAdapter
        }

        weatherViewModel.cityForecast.observe(viewLifecycleOwner, ::handleState)

        weatherViewModel.getForecast(cityName)

        return binding.root
    }

    private fun handleState(resultState: ResultState) {
        when(resultState) {
            is ResultState.LOADING -> {
                Toast.makeText(requireContext(), "LOADING...", Toast.LENGTH_LONG).show()
            }
            is ResultState.SUCCESS -> {
                weatherAdapter.setForecast(resultState.results.list)
            }
            is ResultState.ERROR -> {
                Log.e("FORECAST", resultState.error.localizedMessage, resultState.error)
                Toast.makeText(requireContext(), resultState.error.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onItemClick(forecast: Main) {
        findNavController().navigate(R.id.action_ForecastFragment_to_DetailsFragment, bundleOf(Pair("forecast", forecast)))
    }

}

