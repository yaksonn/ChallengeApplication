package com.yakson.sportbetapp.ui.basket

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.yakson.sportbetapp.R
import com.yakson.sportbetapp.base.BaseFragment
import com.yakson.sportbetapp.databinding.FragmentBetBasketBinding
import com.yakson.sportbetapp.util.logFirebaseEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BetBasketFragment : BaseFragment<FragmentBetBasketBinding, BetBasketViewModel>() {
    override val viewModel: BetBasketViewModel by viewModels()
    private val basketAdapter = BasketAdapter(
        onRemove = { outcome ->
            viewModel.removeFromBasket(outcome)
            requireContext().logFirebaseEvent("remove_from_cart_event") {
                putString("outcome_name", outcome.name)
                putDouble("price", outcome.price)
            }
        }
    )

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentBetBasketBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeBasket()
        setupClearButton()
    }

    private fun setupRecyclerView() {
        binding.basketRecyclerView.adapter = basketAdapter
    }

    private fun observeBasket() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.basket.collectLatest { basket ->
                basketAdapter.submitList(basket.toList())
                updateTotalPrice()
                updateEventCount(basket.size)
            }
        }
    }

    private fun updateTotalPrice() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.totalPrice.collectLatest { total ->
                binding.totalPriceTextView.text = getString(R.string.total_price, total)
            }
        }
    }

    private fun updateEventCount(count: Int) {
        binding.eventCountTextView.text = getString(R.string.event_count, count)
    }

    private fun setupClearButton() {
        binding.clearBasket.setOnClickListener {
            viewModel.clearBasket()
            Toast.makeText(requireContext(), getString(R.string.basket_cleared), Toast.LENGTH_SHORT).show()
        }
    }
} 