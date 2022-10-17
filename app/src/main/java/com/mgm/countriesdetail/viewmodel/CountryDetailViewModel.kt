package com.mgm.countriesdetail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mgm.countriesdetail.models.ResponseCountries.*
import com.mgm.countriesdetail.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryDetailViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    val detail = MutableLiveData<ResponseCountriesItem>()
    val isLoading = MutableLiveData<Boolean>()
    val errorString = MutableLiveData<String>()

    fun getDetail(cca3:String) = viewModelScope.launch {
        isLoading.postValue(true)
        val res = repository.getCountryDetail(cca3)
        if (res.isSuccessful && !res.body().isNullOrEmpty() && res.body()!!.size > 0){
            detail.postValue(res.body()!![0])
        }else
            errorString.postValue(res.errorBody().toString())
        isLoading.postValue(false)
    }
}