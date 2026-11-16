package com.androidbook.bookmarket

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class ShippingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shipping)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, 0, bars.right, bars.bottom)
            toolbar.setPadding(0, bars.top, 0, 0)
            insets
        }
        setSupportActionBar(toolbar)
        supportActionBar?.title = "배송정보"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val name: EditText = findViewById(R.id.ship_name)
        val date: EditText = findViewById(R.id.ship_date)
        val phone: EditText = findViewById(R.id.ship_phone)
        val zip: EditText = findViewById(R.id.ship_zip)
        val address: EditText = findViewById(R.id.ship_address)

        // 배송일: 날짜 대화상자
        date.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, day ->
                date.setText("${year}년 ${month + 1}월 ${day}일")
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        // 등록 → 주문서 (입력값을 인텐트로 전달)
        findViewById<Button>(R.id.btn_register).setOnClickListener {
            val fields = listOf(name, date, phone, zip, address)
            if (fields.any { it.text.isBlank() }) {
                Toast.makeText(applicationContext, "배송 정보를 모두 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, OrderActivity::class.java)
            intent.putExtra("name", name.text.toString())
            intent.putExtra("date", date.text.toString())
            intent.putExtra("phone", phone.text.toString())
            intent.putExtra("zip", zip.text.toString())
            intent.putExtra("address", address.text.toString())
            startActivity(intent)
        }

        // 취소 → 장바구니로
        findViewById<Button>(R.id.btn_cancel).setOnClickListener { finish() }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}