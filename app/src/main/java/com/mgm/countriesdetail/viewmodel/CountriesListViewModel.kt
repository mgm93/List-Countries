package com.mgm.countriesdetail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mgm.countriesdetail.models.ResponseCountries
import com.mgm.countriesdetail.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val list = MutableLiveData<List<ResponseCountries.ResponseCountriesItem>>()
    val isLoading = MutableLiveData<Boolean>()

    val searchQuery = MutableLiveData<String>()

    fun getListCountries() = viewModelScope.launch {
        isLoading.postValue(true)
        val res = repository.getAllCountries()
        if (res.isSuccessful){
            list.postValue(res.body())
        }else{
          //todo :show error
        }
        isLoading.postValue(false)
    }

    fun setSearchQuery(query: String)= viewModelScope.launch{
        searchQuery.postValue(query)
    }
}