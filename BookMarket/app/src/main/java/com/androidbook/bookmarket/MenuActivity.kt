package com.androidbook.bookmarket

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
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

        ViewCompat.setOnApplyWindowInsetsListener(drawerLayout) { _, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            toolbar.setPadding(0, bars.top, 0, 0)
            content.setPadding(bars.left, 0, bars.right, bars.bottom)
            insets
        }

        setSupportActionBar(toolbar)
        supportActionBar?.title = "BookMarket"

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // 드로어 메뉴
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener { item ->
            drawerLayout.closeDrawers()
            when (item.itemId) {
                R.id.nav_login -> showLoginDialog()
                R.id.nav_myinfo -> startActivity(Intent(this, MyPageActivity::class.java))
                else -> Toast.makeText(applicationContext, item.title, Toast.LENGTH_SHORT).show()
            }
            true
        }

        // 도서목록
        findViewById<LinearLayout>(R.id.menu_booklist).setOnClickListener {
            startActivity(Intent(this, BookListActivity::class.java))
        }

        // 장바구니
        findViewById<LinearLayout>(R.id.menu_cart).setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        // 나의페이지
        findViewById<LinearLayout>(R.id.menu_mypage).setOnClickListener {
            startActivity(Intent(this, MyPageActivity::class.java))
        }

        // 나머지 메뉴
        val menus = mapOf(
            R.id.menu_ebook to "E-Book",
            R.id.menu_market to "마켓정보",
            R.id.menu_service to "고객센터"
        )
        for ((id, name) in menus) {
            findViewById<LinearLayout>(id).setOnClickListener {
                Toast.makeText(applicationContext, "$name 메뉴를 클릭했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 로그인 대화상자 + 아이디 저장 (공유 환경 설정)
    private fun showLoginDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_login, null)
        builder.setView(dialogView)
        val dialog = builder.show()

        val loginButton: Button = dialogView.findViewById(R.id.login_button)
        loginButton.setOnClickListener {
            val id = dialogView.findViewById<EditText>(R.id.login_id).text.toString()
            val pw = dialogView.findViewById<EditText>(R.id.login_pw).text.toString()

            val pref = getSharedPreferences("login", MODE_PRIVATE)
            pref.edit().putString("id", id).apply()

            Toast.makeText(applicationContext, "아이디: $id 비밀번호: $pw", Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }
    }
}