package com.yakson.sportbetapp.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yakson.sportbetapp.base.BaseAdapter
import com.yakson.sportbetapp.base.BaseViewHolder
import com.yakson.sportbetapp.databinding.ItemOddBinding
import com.yakson.sportbetapp.model.Outcome

class OddsAdapter(
    private val onAddToBasket: (Outcome) -> Unit
) : BaseAdapter<Outcome, ItemOddBinding, OddsAdapter.OddsViewHolder>() {

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup): ItemOddBinding {
        return ItemOddBinding.inflate(inflater, parent, false)
    }

    override fun createViewHolder(binding: ItemOddBinding): OddsViewHolder {
        return OddsViewHolder(binding)
    }

    override fun bind(holder: OddsViewHolder, item: Outcome) {
        holder.binding.oddTypeTextView.text = item.name
        holder.binding.oddValueTextView.text = item.price.toString()
        holder.binding.root.setOnClickListener { onAddToBasket(item) }
    }

    class OddsViewHolder(binding: ItemOddBinding) : BaseViewHolder<ItemOddBinding>(binding)
} 