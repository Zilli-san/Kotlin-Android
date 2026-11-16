package com.androidbook.bookmarket

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, 0, bars.right, bars.bottom)
            toolbar.setPadding(0, bars.top, 0, 0)
            insets
        }
        setSupportActionBar(toolbar)
        supportActionBar?.title = "주문서"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 배송정보 화면에서 받은 값
        val name = intent.getStringExtra("name") ?: ""
        val date = intent.getStringExtra("date") ?: ""
        val phone = intent.getStringExtra("phone") ?: ""
        val zip = intent.getStringExtra("zip") ?: ""
        val address = intent.getStringExtra("address") ?: ""

        findViewById<TextView>(R.id.order_date).text = "배송일 : $date"
        findViewById<TextView>(R.id.order_address).text =
            "성명 : $name($phone)\n우편번호 : $zip\n주소 : $address"

        // 선택한 도서 목록 표시
        val container: LinearLayout = findViewById(R.id.order_items)
        for (item in CartManager.checkedItems()) {
            val row = layoutInflater.inflate(R.layout.item_order, container, false)
            row.findViewById<TextView>(R.id.order_title).text = item.book.title
            row.findViewById<TextView>(R.id.order_bookid).text = item.book.id
            row.findViewById<TextView>(R.id.order_price).text = item.book.price.toString()
            row.findViewById<TextView>(R.id.order_qty).text = item.quantity.toString()
            row.findViewById<TextView>(R.id.order_subtotal).text = item.subtotal.toString()
            container.addView(row)
        }
        findViewById<TextView>(R.id.order_total).text = "총액 : ${CartManager.checkedTotal()}"

        // 주문완료 → 대화상자 → 주문한 도서를 장바구니에서 제거하고 메뉴로
        findViewById<Button>(R.id.btn_order_done).setOnClickListener {
            AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("도서 주문 완료")
                .setMessage("도서를 주문해 주셔서 감사합니다.")
                .setNegativeButton("아니요", null)
                .setPositiveButton("예") { _, _ ->
                    CartManager.items.removeAll { it.checked }
                    goHome()
                }
                .show()
        }

        // 주문취소 → 장바구니 비우기
        findViewById<Button>(R.id.btn_order_cancel).setOnClickListener {
            CartManager.clear()
            Toast.makeText(applicationContext, "주문이 취소되어 장바구니를 비웠습니다.", Toast.LENGTH_SHORT).show()
            goHome()
        }
    }

    private fun goHome() {
        startActivity(Intent(this, MenuActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}