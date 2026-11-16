package com.androidbook.bookmarket

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {

    private lateinit var adapter: CartAdapter
    private lateinit var checkAll: CheckBox
    private lateinit var totalText: TextView
    private lateinit var emptyText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, 0, bars.right, bars.bottom)
            toolbar.setPadding(0, bars.top, 0, 0)
            insets
        }
        setSupportActionBar(toolbar)
        supportActionBar?.title = "장바구니"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        checkAll = findViewById(R.id.check_all)
        totalText = findViewById(R.id.total_text)
        emptyText = findViewById(R.id.empty_text)

        // 리사이클러 뷰 + 어댑터 연결
        val recyclerView: RecyclerView = findViewById(R.id.cart_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CartAdapter(
            CartManager.items,
            onChanged = { updateSummary() },
            onDelete = { position -> confirmDelete(position) }
        )
        recyclerView.adapter = adapter

        // 전체 선택
        checkAll.setOnClickListener {
            val checked = checkAll.isChecked
            CartManager.items.forEach { it.checked = checked }
            adapter.notifyDataSetChanged()
            updateSummary()
        }

        // 주문하기 → 배송정보
        findViewById<Button>(R.id.btn_order).setOnClickListener {
            if (CartManager.checkedItems().isEmpty()) {
                Toast.makeText(applicationContext, "주문할 도서를 선택하세요.", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, ShippingActivity::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
        updateSummary()
    }

    // 도서 1권 삭제
    private fun confirmDelete(position: Int) {
        if (position == RecyclerView.NO_POSITION) return
        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("도서 삭제")
            .setMessage("선택한 도서를 삭제하겠습니까?")
            .setNegativeButton("아니요", null)
            .setPositiveButton("예") { _, _ ->
                CartManager.items.removeAt(position)
                adapter.notifyItemRemoved(position)
                updateSummary()
            }
            .show()
    }

    // 합계, 전체 선택 체크, 빈 장바구니 안내
    private fun updateSummary() {
        totalText.text = "합계 : ${CartManager.checkedTotal()}"
        checkAll.isChecked = CartManager.items.isNotEmpty() && CartManager.items.all { it.checked }
        emptyText.visibility = if (CartManager.items.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.action_delete_checked -> {
                if (CartManager.checkedItems().isEmpty()) {
                    Toast.makeText(applicationContext, "삭제할 도서를 선택하세요.", Toast.LENGTH_SHORT).show()
                } else {
                    AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("도서 삭제")
                        .setMessage("선택한 도서를 모두 삭제하겠습니까?")
                        .setNegativeButton("아니요", null)
                        .setPositiveButton("예") { _, _ ->
                            CartManager.items.removeAll { it.checked }
                            adapter.notifyDataSetChanged()
                            updateSummary()
                        }
                        .show()
                }
            }
        }
        return true
    }
}