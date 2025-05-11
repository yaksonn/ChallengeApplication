package com.yakson.sportbetapp.ui.bulletin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yakson.sportbetapp.R
import com.yakson.sportbetapp.base.BaseFragment
import com.yakson.sportbetapp.databinding.FragmentBetBulletinBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BetBulletinFragment : BaseFragment<FragmentBetBulletinBinding, BetBulletinViewModel>() {
    override val viewModel: BetBulletinViewModel by viewModels()
    private val bulletinAdapter = EventAdapter(
        onEventClick = { event ->
            navigateToEventDetail(event.id)
        }
    )

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentBetBulletinBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeEvents()
        setupSearchView()
        viewModel.fetchEvents()
    }

    private fun setupRecyclerView() = with(binding) {
        eventsRecyclerView.adapter = bulletinAdapter
    }

    private fun observeEvents() = with(viewModel) {
        events.observe(viewLifecycleOwner) { events ->
            bulletinAdapter.submitList(events)
        }
    }

    private fun setupSearchView() = with(binding) {
        searchEditText.addTextChangedListener {
            val query = it.toString()
            viewModel.searchEvents(query)
        }
    }

    private fun navigateToEventDetail(eventId: String) {
        val action =
            BetBulletinFragmentDirections.actionBetBulletinFragmentToBetDetailFragment(eventId)
        findNavController().navigate(action)
    }
} 