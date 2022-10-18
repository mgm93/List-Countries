package com.mgm.countriesdetail.ui.country_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.mgm.countriesdetail.databinding.FragmentCountryDetailsBinding
import com.mgm.countriesdetail.models.ResponseCountries
import com.mgm.countriesdetail.viewmodel.CountryDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryDetailsFragment : Fragment() {
    //Binding
    private lateinit var binding: FragmentCountryDetailsBinding

    //Other
    private val viewModel: CountryDetailViewModel by viewModels()
    private val args: CountryDetailsFragmentArgs by navArgs()
    private lateinit var ccs3: String

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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //observe detail
            viewModel.detail.observe(viewLifecycleOwner) {
                imgCover.load(it.flags.png) {
                    crossfade(true)
                    crossfade(100)
                }
                txtName.text = "${it!!.name.official}(${it.name.common})"
                imgFlag.load(it.flags.png) {
                    crossfade(true)
                    crossfade(100)
                }
                val capital = if(it.capital.isNotEmpty()) it.capital[0] else ""
                txtCapital.text = "Capital: $capital"
                txtRegion.text = "Region: ${it.region}"
                txtSubRegion.text = "Sub Region: ${it.subregion}"
                txtPopulation.text = "Population: ${it.population}"
                txtStartOfWeek.text = "Start Of Week: ${it.startOfWeek}"
                txtLanguages.text = "Languages: ${getStringLanguage(it.languages)}"
                txtCurrencies.text = "Currencies: ${getStringCurrency(it.currencies)}"
                txtTimezones.text = "Timezones: ${it.timezones}"
            }
            //Loading
            viewModel.isLoading.observe(viewLifecycleOwner) {
                if (it) {
                    loading.visibility = View.VISIBLE
                    layDetail.visibility = View.GONE
                } else {
                    loading.visibility = View.GONE
                    layDetail.visibility = View.VISIBLE
                }
            }
            //Error
            viewModel.errorString.observe(viewLifecycleOwner) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun getStringLanguage(languages: HashMap<String, String>):String{
        val stringBuilder = StringBuilder()
        if (languages.isNotEmpty())

            languages.keys.let {

                for ((i, key) in it.withIndex()) {
                    val langValue =languages[key]

                    stringBuilder.append("${key}(${langValue})")
                    if (it.size > 1 && i < it.size - 1)
                        stringBuilder.append(" , ")
                }
            }
        return stringBuilder.toString()
    }

    private fun getStringCurrency(currencies: HashMap<String, ResponseCountries.ResponseCountriesItem.CurrencyDetail>):String{
        val stringBuilder = StringBuilder()
        if (currencies.isNotEmpty())

            currencies.keys.let {

                for ((i, key) in it.withIndex()) {
                    val currencyName = currencies[key]?.name
                    val currencySymbol =currencies[key]?.symbol

                    stringBuilder.append("${currencyName}(${currencySymbol})")
                    if (it.size > 1 && i < it.size - 1)
                        stringBuilder.append(" , ")
                }
            }
        return stringBuilder.toString()
    }

}