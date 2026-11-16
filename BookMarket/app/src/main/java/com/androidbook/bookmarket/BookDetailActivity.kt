package com.androidbook.bookmarket

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BookDetailActivity : AppCompatActivity() {

    private var cartBadge: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_book_detail)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, 0, bars.right, bars.bottom)
            toolbar.setPadding(0, bars.top, 0, 0)
            insets
        }

        setSupportActionBar(toolbar)
        supportActionBar?.title = "도서상세정보"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val index = intent.getIntExtra("bookIndex", 0)
        val book = BookData.books[index]

        findViewById<ImageView>(R.id.detail_image).setImageResource(book.imageRes)
        findViewById<TextView>(R.id.detail_id).text = book.id
        findViewById<TextView>(R.id.detail_title).text = book.title
        findViewById<TextView>(R.id.detail_price).text = "%,d원".format(book.price)
        findViewById<TextView>(R.id.detail_date).text = book.date
        findViewById<TextView>(R.id.detail_author).text = book.author
        findViewById<TextView>(R.id.detail_desc).text = book.description
        findViewById<TextView>(R.id.detail_category).text = book.category

        // 장바구니담기 → 기본 대화상자 (8장 02절 방식)
        findViewById<Button>(R.id.btn_add_cart).setOnClickListener {
            AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("장바구니 담기")
                .setMessage("도서를 장바구니에 추가하겠습니까?")
                .setNegativeButton("아니요", null)
                .setPositiveButton("예") { _, _ ->
                    CartManager.add(book)
                    updateBadge()
                    Toast.makeText(applicationContext, book.title, Toast.LENGTH_SHORT).show()
                }
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        updateBadge()   // 장바구니 화면에서 돌아왔을 때 개수 갱신
    }

    private fun updateBadge() {
        val count = CartManager.count()
        cartBadge?.text = count.toString()
        cartBadge?.visibility = if (count > 0) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)

        // 장바구니 아이콘(배지 포함) 연결
        val cartView = menu.findItem(R.id.action_cart).actionView
        cartBadge = cartView?.findViewById(R.id.cart_badge)
        cartView?.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
        updateBadge()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.menu_home -> {
                startActivity(Intent(this, MenuActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
        }
        Toast.makeText(applicationContext, item.title, Toast.LENGTH_SHORT).show()
        return true
    }
}