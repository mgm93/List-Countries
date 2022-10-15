package com.mgm.countriesdetail.repository

import com.mgm.countriesdetail.api.ApiService
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {
    //Api
    suspend fun getAllCountries() = apiService.getAllCountries()
    suspend fun getCountryDetail(code:String) = apiService.getCountryDetail(code)
}