package com.mgm.countriesdetail.ui.list_countries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mgm.countriesdetail.databinding.FragmentListCountriesBinding
import com.mgm.countriesdetail.ui.list_countries.adapter.ListCountryAdapter
import com.mgm.countriesdetail.viewmodel.CountriesListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CountriesListFragment : Fragment() {
    //Binding
    private lateinit var binding: FragmentListCountriesBinding

    @Inject
    lateinit var listCountryAdapter: ListCountryAdapter

    //Other
    private val viewModel : CountriesListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListCountriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Call get All countries
        viewModel.getListCountries()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            viewModel.list.observe(viewLifecycleOwner) {
                //set date to adapter
                listCountryAdapter.differ.submitList(it)
                //init Built dates recycler
                recyclerCountries.apply { adapter = listCountryAdapter }
            }
        }
    }

}