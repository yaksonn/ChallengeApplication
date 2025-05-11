package com.yakson.sportbetapp.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, VB : ViewBinding, VH : BaseViewHolder<VB>>(
    private var items: List<T> = emptyList()
) : RecyclerView.Adapter<VH>() {

    override fun getItemCount(): Int = items.size

    fun submitList(newItems: List<T>) {
        items = newItems
        notifyDataSetChanged()
    }

    protected fun getItem(position: Int): T = items[position]

    abstract fun createBinding(inflater: LayoutInflater, parent: ViewGroup): VB
    abstract fun createViewHolder(binding: VB): VH
    abstract fun bind(holder: VH, item: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = createBinding(LayoutInflater.from(parent.context), parent)
        return createViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        bind(holder, getItem(position))
    }
} 