package com.mgm.countriesdetail.api

import com.mgm.countriesdetail.models.ResponseCountries
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("all")
    suspend fun getAllCountries(): Response<ResponseCountries>

    @GET("alpha/{code}")
    suspend fun getCountryDetail(@Path("code") code : String): Response<ResponseCountries>
}