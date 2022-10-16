package com.mgm.countriesdetail.ui.list_countries.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mgm.countriesdetail.databinding.ItemCountryBinding
import com.mgm.countriesdetail.models.CountryInfo
import com.mgm.countriesdetail.models.ResponseCountries.*
import javax.inject.Inject

/**
 * Created by Majid-Golmoradi on 10/15/2022.
 * Email: golmoradi.majid@gmail.com
 */
class ListCountryAdapter @Inject constructor(): RecyclerView.Adapter<ListCountryAdapter.ViewHolder>(), Filterable {
    //Binding
    private lateinit var binding : ItemCountryBinding
    //Other
    private val searchableList = ArrayList<ResponseCountriesItem>()
    private val originalList = ArrayList(searchableList)
    private var searchResultCallBack: ((isDataFound: Boolean) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListCountryAdapter.ViewHolder {
        binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ListCountryAdapter.ViewHolder, position: Int) {
        holder.bind(searchableList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(list :List<ResponseCountriesItem>){
        this.searchableList.clear()
        list.let {
            this.searchableList.addAll(it)
            originalList.addAll(it)
            differ.submitList(searchableList)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount()= searchableList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(item : ResponseCountriesItem){
            binding.apply {
                officialName.text = item.name.official
                var capitalName = ""
                if (item.capital.isNotEmpty())
                    capitalName = "Capital: ${item.capital[0]}"
                capital.text = "Capital: $capitalName"
                region.text = "Region: ${item.region}"
                flag.load(item.flags.png) {
                    crossfade(true)
                    crossfade(100)
                }
                //click
                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item.cca3)
                    }
                }
            }
        }
    }

    //onClick
    private var onItemClickListener : ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit){
        onItemClickListener = listener
    }

    private val differCallBack = object : DiffUtil.ItemCallback<ResponseCountriesItem>(){
        override fun areItemsTheSame(oldItem: ResponseCountriesItem, newItem: ResponseCountriesItem): Boolean {
            return oldItem.name.official == newItem.name.official
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ResponseCountriesItem, newItem: ResponseCountriesItem): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, differCallBack)

    override fun getFilter(): Filter {
        return object : Filter(){
            private val filterResults = FilterResults()
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                searchableList.clear()
                if (constraint.isNullOrBlank()) {
                    searchableList.addAll(originalList)
                } else {
                    val searchResults =
                        originalList.filter {
                            it.name.official.lowercase().contains(charString.lowercase())
                        }
                    searchableList.addAll(searchResults)
                }
                return filterResults.also {
                    it.values = searchableList
                    differ.submitList(searchableList)
                }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                // no need to use "results" filtered list provided by this method.
                searchResultCallBack?.invoke(searchableList.isNullOrEmpty())

                notifyDataSetChanged()
            }

        }
    }

    fun search(s: String?, onNothingFound: ((isDataFound: Boolean) -> Unit)?) {
        this.searchResultCallBack = onNothingFound
        filter.filter(s)
    }

}