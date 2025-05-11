package com.yakson.sportbetapp.ui.bulletin

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yakson.sportbetapp.base.BaseAdapter
import com.yakson.sportbetapp.base.BaseViewHolder
import com.yakson.sportbetapp.databinding.ItemEventBinding
import com.yakson.sportbetapp.model.Event
import com.yakson.sportbetapp.util.loadTeamLogo

class EventAdapter(
    private val onEventClick: (Event) -> Unit
) : BaseAdapter<Event, ItemEventBinding, EventAdapter.EventViewHolder>() {

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup): ItemEventBinding {
        return ItemEventBinding.inflate(inflater, parent, false)
    }

    override fun createViewHolder(binding: ItemEventBinding): EventViewHolder {
        return EventViewHolder(binding)
    }

    override fun bind(holder: EventViewHolder, item: Event) {
        holder.binding.apply {
            homeTeamTextView.text = item.home_team
            homeTeamTextView.loadTeamLogo(item.home_team_logo)
            awayTeamTextView.text = item.away_team
            awayTeamTextView.loadTeamLogo(item.away_team_logo)
            root.setOnClickListener { onEventClick(item) }
        }
    }

    class EventViewHolder(binding: ItemEventBinding) : BaseViewHolder<ItemEventBinding>(binding)
} 