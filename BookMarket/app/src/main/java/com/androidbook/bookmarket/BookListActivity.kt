package com.androidbook.bookmarket

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BookListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_book_list)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, 0, bars.right, bars.bottom)
            toolbar.setPadding(0, bars.top, 0, 0)
            insets
        }

        // 액션 바 설정: 제목 + ← 뒤로가기
        setSupportActionBar(toolbar)
        supportActionBar?.title = "도서목록"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 도서 목록 채우기
        val bookGrid: GridLayout = findViewById(R.id.bookGrid)
        for ((index, book) in BookData.books.withIndex()) {
            val item = layoutInflater.inflate(R.layout.item_book, bookGrid, false)
            item.findViewById<ImageView>(R.id.bookImage).setImageResource(book.imageRes)
            item.findViewById<TextView>(R.id.bookTitle).text = book.title
            item.findViewById<TextView>(R.id.bookPrice).text = "정가: %,d원".format(book.price)

            // 도서 클릭 → 도서 상세 정보 화면 (명시적 인텐트 + 데이터 전달)
            item.setOnClickListener {
                val intent = Intent(this, BookDetailActivity::class.java)
                intent.putExtra("bookIndex", index)
                startActivity(intent)
            }

            val params = GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED),
                GridLayout.spec(GridLayout.UNDEFINED, 1f)
            )
            params.width = 0
            item.layoutParams = params
            bookGrid.addView(item)
        }
    }

    // 옵션 메뉴 생성
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_booklist, menu)
        return true
    }

    // 옵션 메뉴 항목 선택
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {          // ← 뒤로가기
                finish()
                return true
            }
            R.id.menu_home -> {             // 홈으로 → 메뉴 화면
                startActivity(Intent(this, MenuActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
        }
        Toast.makeText(applicationContext, item.title, Toast.LENGTH_SHORT).show()
        return true
    }
}