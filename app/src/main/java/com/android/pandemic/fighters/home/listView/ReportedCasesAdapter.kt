package com.android.pandemic.fighters.home.listView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.pandemic.fighters.databinding.ViewItemListBinding
import com.android.pandemic.fighters.home.models.Fields
import com.android.pandemic.fighters.utils.DateFormatter

class ReportedCasesAdapter(
) : ListAdapter<Fields, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Fields>() {
            override fun areItemsTheSame(
                oldItem: Fields,
                newItem: Fields
            ) = oldItem.timestamp == newItem.timestamp

            override fun areContentsTheSame(
                oldItem: Fields,
                newItem: Fields
            ) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ReportedCasesVIewHolder(
            ViewItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as ReportedCasesVIewHolder).bind(getItem(position) as Fields)
}

class ReportedCasesVIewHolder(private var binding: ViewItemListBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Fields) {
        binding.apply {
            tvTitle.text = item.address.value
            tvDate.text = DateFormatter.getDataFromTimestamp(item.timestamp.value.toLong())
            tvDescription.text = item.description.value
        }
    }
}