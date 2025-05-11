package com.yakson.sportbetapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.yakson.sportbetapp.base.BaseFragment
import com.yakson.sportbetapp.databinding.FragmentBetDetailBinding
import com.yakson.sportbetapp.model.Outcome
import com.yakson.sportbetapp.ui.basket.BetBasketViewModel
import com.yakson.sportbetapp.util.formatDate
import com.yakson.sportbetapp.util.logFirebaseEvent
import com.yakson.sportbetapp.util.showView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BetDetailFragment : BaseFragment<FragmentBetDetailBinding, BetDetailViewModel>() {

    override val viewModel: BetDetailViewModel by viewModels()
    private val basketViewModel: BetBasketViewModel by viewModels()
    private val args: BetDetailFragmentArgs by navArgs()
    private val outCome: ArrayList<Outcome> = arrayListOf()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBetDetailBinding = FragmentBetDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() = with(binding) {

        requireContext().logFirebaseEvent("match_detail_event") {
            putString("event_id", args.eventId)
        }

        val eventId = args.eventId
        viewModel.getEventById(eventId)

        viewModel.event.observe(viewLifecycleOwner) { event ->
            if (event != null) {
                eventTitleTextView.text = "${event.home_team} - ${event.away_team}"
                eventDetailTextView.text = "Başlangıç: ${event.commence_time.formatDate()}"

                val allOutcomes = event.bookmakers
                    .flatMap { it.markets }
                    .flatMap { it.outcomes }
                val oddsAdapter = OddsAdapter { outcome ->
                    outCome.add(outcome)
                    requireContext().logFirebaseEvent("add_to_cart_event") {
                        putString("outcome_name", outcome.name)
                        putDouble("price", outcome.price)
                    }
                }
                oddsRecyclerView.adapter = oddsAdapter
                oddsAdapter.submitList(allOutcomes)

                emptyState.showView(allOutcomes.isNullOrEmpty())
                oddsRecyclerView.showView(allOutcomes.isNullOrEmpty().not())


                addToBasketButton.setOnClickListener {
                    outCome.firstOrNull()?.let { outcome ->
                        basketViewModel.addToBasket(outcome)
                    }
                }
            }
        }
    }

} 