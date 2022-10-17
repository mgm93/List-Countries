package com.mgm.countriesdetail.models

import androidx.databinding.Bindable

class ResponseCountries : ArrayList<ResponseCountries.ResponseCountriesItem>(){
    data class ResponseCountriesItem(
        var id: Int? = null,
        val capital: List<String> = listOf(),
        val cca2: String = "",
        val cca3: String = "",
        val ccn3: String = "",
        val cioc: String = "",
        val flags: Flags = Flags(),
        val languages: HashMap<String, String> = HashMap(),
        var currencies: HashMap<String, CurrencyDetail> = HashMap(),
        val name: Name = Name(),
        val population: Int = 0,
        val region: String = "",
        val startOfWeek: String = "",
        val subregion: String = "",
        val timezones: List<String> = listOf(),
        val continents: List<String> = listOf()
    ) {
        data class CurrencyDetail(
            val name: String?,
            val symbol: String?
        )
    
        data class Flags(
            val png: String = "",
            val svg: String = ""
        )

        data class Name(
            val common: String = "",
            val official: String = ""
        )

    }
}