package com.androidbook.bookmarket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private val items: MutableList<CartItem>,
    private val onChanged: () -> Unit,        // 체크 변경 시 합계 갱신
    private val onDelete: (Int) -> Unit       // 삭제 버튼 클릭 시
) : RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val check: CheckBox = itemView.findViewById(R.id.item_check)
        val image: ImageView = itemView.findViewById(R.id.item_image)
        val title: TextView = itemView.findViewById(R.id.item_title)
        val price: TextView = itemView.findViewById(R.id.item_price)
        val qty: TextView = itemView.findViewById(R.id.item_qty)
        val subtotal: TextView = itemView.findViewById(R.id.item_subtotal)
        val delete: ImageButton = itemView.findViewById(R.id.item_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        holder.image.setImageResource(item.book.imageRes)
        holder.title.text = item.book.title
        holder.price.text = "가격 : ${item.book.price}"
        holder.qty.text = "수량 : ${item.quantity}"
        holder.subtotal.text = "소계 : ${item.subtotal}"

        holder.check.setOnCheckedChangeListener(null)   // 재사용된 뷰의 이전 리스너 제거
        holder.check.isChecked = item.checked
        holder.check.setOnCheckedChangeListener { _, isChecked ->
            item.checked = isChecked
            onChanged()
        }

        holder.delete.setOnClickListener {
            onDelete(holder.bindingAdapterPosition)
        }
    }

    override fun getItemCount(): Int = items.size
}