package com.androidbook.bookmarket

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        val drawerLayout: DrawerLayout = findViewById(R.id.main)
        val content: LinearLayout = findViewById(R.id.content)
        val toolbar: Toolbar = findViewById(R.id.toolbar)

        // 툴바는 상태 바 아래로, 본문은 내비게이션 바 위로
        ViewCompat.setOnApplyWindowInsetsListener(drawerLayout) { _, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            toolbar.setPadding(0, bars.top, 0, 0)
            content.setPadding(bars.left, 0, bars.right, bars.bottom)
            insets
        }

        // 툴바를 앱 바로 설정
        setSupportActionBar(toolbar)
        supportActionBar?.title = "BookMarket"

        // ☰ 아이콘을 누르면 드로어 열기/닫기
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // 드로어 메뉴 항목 클릭
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener { item ->
            Toast.makeText(applicationContext, item.title, Toast.LENGTH_SHORT).show()
            drawerLayout.closeDrawers()
            true
        }

        // 도서목록 → 도서목록 화면
        findViewById<LinearLayout>(R.id.menu_booklist).setOnClickListener {
            startActivity(Intent(this, BookListActivity::class.java))
        }

        // 나머지 메뉴: 토스트
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