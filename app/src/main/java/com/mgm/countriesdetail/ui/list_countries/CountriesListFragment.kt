package com.mgm.countriesdetail.ui.list_countries

import android.os.Bundle
import android.util.Log
import android.view.*
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
    private lateinit var searchView: SearchView

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
            viewModel.searchQuery.observe({ lifecycle }) {
                listCountryAdapter.search(it) { notFound ->

                }
            }

        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        val pendingQuery = viewModel.searchQuery.value
        if (pendingQuery != null && pendingQuery.isNotEmpty()) {
            searchItem.expandActionView()
            searchView.setQuery(pendingQuery, false)
        }
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