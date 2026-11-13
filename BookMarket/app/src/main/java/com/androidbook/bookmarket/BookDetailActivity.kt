package com.androidbook.bookmarket

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BookDetailActivity : AppCompatActivity() {

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

        // 도서목록에서 보낸 데이터 받기
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

        Toast.makeText(applicationContext, book.title, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
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