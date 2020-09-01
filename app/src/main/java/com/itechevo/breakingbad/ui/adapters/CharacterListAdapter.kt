package com.itechevo.breakingbad.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.itechevo.breakingbad.R
import com.itechevo.domain.model.Character
import kotlinx.android.synthetic.main.character_item.view.*

class CharacterListAdapter(private val clickListener: ((Character) -> Unit)? = null) :
    RecyclerView.Adapter<CharacterListAdapter.ViewHolder>() {

    var data: List<Character> = listOf()
        set(value) {
            dataFiltered.clear()
            dataFiltered.addAll(value)

            field = value

            //TODO DiffUtils
            notifyDataSetChanged()
        }

    var dataFiltered: MutableList<Character> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.character_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(dataFiltered[position], clickListener)

    override fun getItemCount() = dataFiltered.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Character, clickListener: ((Character) -> Unit)? = null) =
            with(itemView) {
                characterName.text = item.name
                Glide.with(itemView)
                    .load(item.img)
                    .placeholder(R.drawable.breaking_bad_title_card)
                    .into(characterImage)
                setOnClickListener {
                    clickListener?.let { it(item) }
                }
            }
    }
}