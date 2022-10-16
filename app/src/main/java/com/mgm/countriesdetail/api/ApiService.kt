package com.mgm.countriesdetail.api

import com.mgm.countriesdetail.models.Countries
import com.mgm.countriesdetail.models.CountryInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("all")
    suspend fun getAllCountries(): List<CountryInfo>

    @GET("alpha/{code}")
    suspend fun getCountryDetail(@Path("code") code : String): Response<Countries>
}