package com.yakson.sportbetapp.ui.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yakson.sportbetapp.base.BaseAdapter
import com.yakson.sportbetapp.base.BaseViewHolder
import com.yakson.sportbetapp.databinding.ItemBasketBinding
import com.yakson.sportbetapp.model.Outcome

class BasketAdapter(
    private val onRemove: (Outcome) -> Unit
) : BaseAdapter<Outcome, ItemBasketBinding, BasketAdapter.BasketViewHolder>() {

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup): ItemBasketBinding {
        return ItemBasketBinding.inflate(inflater, parent, false)
    }

    override fun createViewHolder(binding: ItemBasketBinding): BasketViewHolder {
        return BasketViewHolder(binding)
    }

    override fun bind(holder: BasketViewHolder, item: Outcome) {
        holder.binding.apply {
            basketEventNameTextView.text = item.name
            basketOddTextView.text = item.price.toString()
            closeButton.setOnClickListener { onRemove(item) }
        }
    }

    class BasketViewHolder(binding: ItemBasketBinding) : BaseViewHolder<ItemBasketBinding>(binding)
} 