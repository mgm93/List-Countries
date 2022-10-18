package com.mgm.countriesdetail.ui.list_countries.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mgm.countriesdetail.databinding.ItemCountryBinding
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
        holder.setIsRecyclable(false)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(list :List<ResponseCountriesItem>){
        this.searchableList.clear()
        this.originalList.clear()
        list.let {
            this.searchableList.addAll(it)
            originalList.addAll(it)
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
                    capitalName = item.capital[0]
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

    override fun getFilter(): Filter {
        return object : Filter(){
            private val filterResults = FilterResults()
            @SuppressLint("NotifyDataSetChanged")
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
                    searchableList.clear()
                    searchableList.addAll(searchResults)
                }
                return filterResults.also {
                    it.values = searchableList
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