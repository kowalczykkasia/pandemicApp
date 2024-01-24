package com.android.pandemic.fighters.home.listView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.pandemic.fighters.base.BaseFragment
import com.android.pandemic.fighters.databinding.FragmentHomeListViewBinding
import com.android.pandemic.fighters.utils.extensions.handleResponseState
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeListViewFragment : BaseFragment<FragmentHomeListViewBinding>() {

    private val viewModel by viewModels<HomeListViewViewModel>()

    private val adapter by lazy {
        ReportedCasesAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
    }

    private fun sortBy(sortCriteria: String) {
        viewModel.sortBy(sortCriteria)
    }

    private fun initViewModel() {
        viewModel.reportedCasesList.onEach {
            it.handleResponseState(successFun = {
                adapter.submitList(it.map { it.fields })
            })
        }.launchIn(lifecycleScope)
    }

    private fun initView() {
        binding.apply {
            rvList.itemAnimator = null
            rvList.adapter = adapter
            ivBack.setOnClickListener { findNavController().popBackStack() }
            chipFilters.setOnCheckedStateChangeListener { group, checkedIds ->
                checkedIds.firstOrNull()?.let {
                    val selectedChip = group.findViewById<Chip>(it)
                    sortBy((selectedChip.tag as? String) ?: "")
                }
            }
        }
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentHomeListViewBinding = FragmentHomeListViewBinding.inflate(inflater, container, false)
}