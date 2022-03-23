package com.example.myweatherappcat22.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myweatherappcat22.R
import com.example.myweatherappcat22.databinding.FragmentForecastDetailsBinding
import com.example.myweatherappcat22.model.Main

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ForecastDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForecastDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val binding by lazy {
        FragmentForecastDetailsBinding.inflate(layoutInflater)
    }

    private lateinit var forecast: Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
           forecast=it.getParcelable("forecast")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.temp.text=String.format("%.2f",(forecast.temp-273.15)*1.8 + 32) + " F"
        binding.feelsLike.text=String.format("%.2f",(forecast.feelsLike-273.15)*1.8 + 32) + " F"



        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FforecastDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ForecastDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}