package com.example.mytrawlbenstechnicaltest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrawlbenstechnicaltest.R
import com.example.mytrawlbenstechnicaltest.entity.CommentEntity

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.ValueViewHolder>() {
    private lateinit var mlistener : onItemClickListener
    private var dataList = ArrayList<CommentEntity>()

    fun setListItem(items: List<CommentEntity>) {
        dataList.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mlistener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ValueViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        return ValueViewHolder(v, mlistener)
    }

    override fun onBindViewHolder(holder: CommentAdapter.ValueViewHolder, position: Int) {
        holder.tvEmail.text = dataList?.get(position)?.email
        holder.tvName.text = dataList?.get(position)?.name
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ValueViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        var tvEmail: TextView = itemView.findViewById(R.id.tv_email)
        var tvName: TextView = itemView.findViewById(R.id.tv_name)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}