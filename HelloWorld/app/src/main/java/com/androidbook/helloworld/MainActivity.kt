package com.androidbook.helloworld

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left + 16, systemBars.top + 16, systemBars.right + 16, systemBars.bottom)
            insets
        }

        val editTo: EditText = findViewById(R.id.editText_to)
        val editSubject: EditText = findViewById(R.id.editText_subject)
        val editBody: EditText = findViewById(R.id.editText_body)
        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            // 암시적 인텐트: "텍스트를 보내는(ACTION_SEND)" 작업을 할 수 있는 앱을 찾음
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"                                            // <data mimeType>
                putExtra(Intent.EXTRA_EMAIL, arrayOf(editTo.text.toString()))  // 받는 사람 (배열!)
                putExtra(Intent.EXTRA_SUBJECT, editSubject.text.toString())    // 제목
                putExtra(Intent.EXTRA_TEXT, editBody.text.toString())          // 내용
            }
            startActivity(Intent.createChooser(intent, "메일 앱 선택"))
        }
    }
}