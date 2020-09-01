package com.itechevo.breakingbad.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itechevo.breakingbad.R
import kotlinx.android.synthetic.main.season_item.view.*

class SeasonListAdapter(private val clickListener: ((Int) -> Unit)? = null) :
    RecyclerView.Adapter<SeasonListAdapter.ViewHolder>() {

    var selectedSeasons = listOf<Int>()

    var data: List<Int> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.season_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(selectedSeasons, data[position], clickListener)

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(selectedSeasons: List<Int>, item: Int, clickListener: ((Int) -> Unit)? = null) =
            with(itemView) {
                season.text = item.toString()
                season.isSelected = selectedSeasons.contains(item)
                setOnClickListener {
                    season.isSelected = !season.isSelected
                    clickListener?.let { it(item) }
                }
            }
    }
}