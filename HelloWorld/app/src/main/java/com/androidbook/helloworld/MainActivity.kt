package com.androidbook.helloworld

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var editText1: EditText
    lateinit var editText2: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left + 16, systemBars.top + 16, systemBars.right + 16, systemBars.bottom)
            insets
        }

        editText1 = findViewById(R.id.editText1)
        editText2 = findViewById(R.id.editText2)
    }

    fun onButtonClick(view: View?) {
        val count1 = editText1.text.toString().toIntOrNull()
        val count2 = editText2.text.toString().toIntOrNull()

        if (count1 == 2 && count2 == 6) {
            Toast.makeText(applicationContext, "맞았습니다", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "틀렸습니다", Toast.LENGTH_SHORT).show()
        }
    }
}