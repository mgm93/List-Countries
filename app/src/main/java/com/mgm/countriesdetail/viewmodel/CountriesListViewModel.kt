package com.mgm.countriesdetail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mgm.countriesdetail.models.CountryInfo
import com.mgm.countriesdetail.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val list = MutableLiveData<List<CountryInfo>>()
    val isLoading = MutableLiveData<Boolean>()

    fun getListCountries() = viewModelScope.launch {
        isLoading.postValue(true)
        val res = repository.getAllCountries()
        list.postValue(res)
        /*if (res.isSuccessful){
            list.postValue(res.body())
        }else{
          //todo :show error
        }*/
        isLoading.postValue(false)
    }
}