package com.mgm.countriesdetail.ui.list_countries.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mgm.countriesdetail.databinding.ItemCountryBinding
import com.mgm.countriesdetail.models.CountryInfo
import javax.inject.Inject

/**
 * Created by Majid-Golmoradi on 10/15/2022.
 * Email: golmoradi.majid@gmail.com
 */
class ListCountryAdapter @Inject constructor(): RecyclerView.Adapter<ListCountryAdapter.ViewHolder>() {
    //Binding
    private lateinit var binding : ItemCountryBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListCountryAdapter.ViewHolder {
        binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ListCountryAdapter.ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount()= differ.currentList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(item : CountryInfo){
            binding.apply {
                officialName.text = item.name!!.official
                capital.text = "Capital: ${item.capital?.get(0)}"
                region.text = "Region: ${item.region}"
                flag.load(item.flags?.png) {
                    crossfade(true)
                    crossfade(500)
                }
                //click
                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item.name!!.common)
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

    private val differCallBack = object : DiffUtil.ItemCallback<CountryInfo>(){
        override fun areItemsTheSame(oldItem: CountryInfo, newItem: CountryInfo): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: CountryInfo, newItem: CountryInfo): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, differCallBack)
}