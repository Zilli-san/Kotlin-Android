package com.androidbook.bookmarket

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 도서목록: 토스트 + 도서목록 화면으로 이동 (5장 p.249)
        findViewById<LinearLayout>(R.id.menu_booklist).setOnClickListener {
            Toast.makeText(applicationContext, "도서목록 메뉴를 클릭했습니다.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, BookListActivity::class.java))
        }

        // 나머지 메뉴: 토스트만 출력
        val menus = mapOf(
            R.id.menu_ebook to "E-Book",
            R.id.menu_market to "마켓정보",
            R.id.menu_mypage to "나의페이지",
            R.id.menu_cart to "장바구니",
            R.id.menu_service to "고객센터"
        )
        for ((id, name) in menus) {
            findViewById<LinearLayout>(id).setOnClickListener {
                Toast.makeText(applicationContext, "$name 메뉴를 클릭했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}