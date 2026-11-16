package com.androidbook.bookmarket

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MemoAdapter : RecyclerView.Adapter<MemoAdapter.MyViewHolder>() {

    private var memos: List<Memo> = emptyList()
    private var selected = RecyclerView.NO_POSITION   // 선택한 메모 위치

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.memo_title)
        val date: TextView = itemView.findViewById(R.id.memo_date)
        val content: TextView = itemView.findViewById(R.id.memo_content)
    }

    fun submit(list: List<Memo>) {
        memos = list
        selected = RecyclerView.NO_POSITION
        notifyDataSetChanged()
    }

    fun selectedMemo(): Memo? = memos.getOrNull(selected)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_memo, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val memo = memos[position]
        holder.title.text = memo.title
        holder.date.text = memo.date
        holder.content.text = memo.content

        // 선택된 메모는 주황색 배경
        holder.itemView.setBackgroundColor(
            if (position == selected) Color.parseColor("#FFE0B2") else Color.WHITE
        )

        // 메모를 누르면 선택 / 다시 누르면 선택 해제
        holder.itemView.setOnClickListener {
            val pos = holder.adapterPosition
            if (pos == RecyclerView.NO_POSITION) return@setOnClickListener
            val old = selected
            selected = if (pos == old) RecyclerView.NO_POSITION else pos
            if (old != RecyclerView.NO_POSITION) notifyItemChanged(old)
            notifyItemChanged(pos)
        }
    }

    override fun getItemCount(): Int = memos.size
}