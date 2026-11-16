package com.androidbook.bookmarket

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MyPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_page)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, 0, bars.right, bars.bottom)
            toolbar.setPadding(0, bars.top, 0, 0)
            insets
        }
        setSupportActionBar(toolbar)
        supportActionBar?.title = "나의 페이지"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 로그인 대화상자에서 저장한 아이디 읽기 (공유 환경 설정)
        val pref = getSharedPreferences("login", MODE_PRIVATE)
        val id = pref.getString("id", "")
        findViewById<TextView>(R.id.my_id).text =
            if (id.isNullOrEmpty()) "로그인이 필요합니다" else id

        // 메모장으로 이동
        findViewById<Button>(R.id.btn_memo).setOnClickListener {
            startActivity(Intent(this, MemoActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}