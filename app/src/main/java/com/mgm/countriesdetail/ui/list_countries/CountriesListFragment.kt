package com.mgm.countriesdetail.ui.list_countries

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mgm.countriesdetail.R
import com.mgm.countriesdetail.databinding.ActivityMainBinding
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
    private val viewModel: CountriesListViewModel by viewModels()

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
            //init Countries recycler
            viewModel.list.observe(viewLifecycleOwner) {
                Log.d("MGM", "Size: ${it.size}")
                //set date to adapter
                listCountryAdapter.addItems(it.sortedBy { o -> o.name.official })
                recyclerCountries.apply { adapter = listCountryAdapter }

            }
            //set search listener
            viewModel.searchQuery.observe(viewLifecycleOwner) {
                listCountryAdapter.search(it) { notFound ->

                }
            }
            setSearchBar()

        }
    }

    //Set up Menu
    private fun setSearchBar() {
        val menuItem: MenuItem? = binding.toolbar.menu.findItem(R.id.action_search)
        val searchView: SearchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.setSearchQuery(newText)
                return true
            }
        })
    }

}