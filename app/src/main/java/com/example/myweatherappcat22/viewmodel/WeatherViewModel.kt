package com.example.myweatherappcat22.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherappcat22.rest.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _cityForecast: MutableLiveData<ResultState> = MutableLiveData(ResultState.LOADING)
    val cityForecast: LiveData<ResultState> get() = _cityForecast

    fun getForecast(city: String) {
        viewModelScope.launch(ioDispatcher) {
            // worker thread
            try {
                val response = weatherRepository.getCityForecast(city)
                if (response.isSuccessful) {
                    response.body()?.let {
                        withContext(Dispatchers.Main) {
                           //main thread
                            _cityForecast.value = ResultState.SUCCESS(it)
                        }
                    } ?: throw Exception("Response null")
                } else {
                    throw Exception("No success")
                }
            } catch (e: Exception) {
               _cityForecast.postValue(ResultState.ERROR(e))
            }
        }
    }
}