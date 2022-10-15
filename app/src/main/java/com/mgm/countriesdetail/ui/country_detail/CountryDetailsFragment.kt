package com.mgm.countriesdetail.ui.country_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mgm.countriesdetail.databinding.FragmentCountryDetailsBinding

class CountryDetailsFragment : Fragment() {
    //Binding
    private lateinit var binding: FragmentCountryDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCountryDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

}