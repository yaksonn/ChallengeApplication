package com.yakson.sportbetapp.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.yakson.sportbetapp.R
import com.yakson.sportbetapp.base.BaseAdapter
import com.yakson.sportbetapp.base.BaseViewHolder
import com.yakson.sportbetapp.databinding.ItemOddBinding
import com.yakson.sportbetapp.model.Outcome

class OddsAdapter(
    private val onAddToBasket: (Outcome) -> Unit
) : BaseAdapter<Outcome, ItemOddBinding, OddsAdapter.OddsViewHolder>() {

    private var selectedPosition = -1

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup): ItemOddBinding {
        return ItemOddBinding.inflate(inflater, parent, false)
    }

    override fun createViewHolder(binding: ItemOddBinding): OddsViewHolder {
        return OddsViewHolder(binding)
    }

    override fun bind(holder: OddsViewHolder, item: Outcome) {
        holder.binding.apply {
            oddTypeTextView.text = item.name
            oddValueTextView.text = item.price.toString()
            root.setBackgroundColor(
                ContextCompat.getColor(
                    root.context,
                    if (holder.adapterPosition == selectedPosition) R.color.selected_odd_background
                    else android.R.color.transparent
                )
            )
            root.setOnClickListener {
                val previousSelected = selectedPosition
                selectedPosition = holder.adapterPosition
                
                if (previousSelected != -1) {
                    notifyItemChanged(previousSelected)
                }
                notifyItemChanged(selectedPosition)
                onAddToBasket(item)
            }
        }
    }

    class OddsViewHolder(binding: ItemOddBinding) : BaseViewHolder<ItemOddBinding>(binding)
} 