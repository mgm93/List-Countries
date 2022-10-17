package com.mgm.countriesdetail.ui.country_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mgm.countriesdetail.databinding.FragmentCountryDetailsBinding
import com.mgm.countriesdetail.viewmodel.CountryDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryDetailsFragment : Fragment() {
    //Binding
    private lateinit var binding: FragmentCountryDetailsBinding

    //Other
    private val viewModel : CountryDetailViewModel by viewModels()
    private val args : CountryDetailsFragmentArgs by navArgs()
    private lateinit var ccs3 :String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCountryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Get Args
        ccs3 = args.cca3
        //Call Api
        viewModel.getDetail(ccs3)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //observe detail
            viewModel.detail.observe(viewLifecycleOwner){
                binding.executePendingBindings()
                txtCapital.text = it!!.capital[0]
            }
            //Loading
            viewModel.isLoading.observe(viewLifecycleOwner){
                if(it){
                    loading.visibility = View.VISIBLE
                    layDetail.visibility = View.GONE
                }else{
                    loading.visibility = View.GONE
                    layDetail.visibility = View.VISIBLE
                }
            }
            //Error
            viewModel.errorString.observe(viewLifecycleOwner){
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }


}