package com.androidbook.helloworld

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {

    // 계산기 상태
    private var current = ""            // 지금 입력 중인 숫자
    private var first: Double? = null   // 첫 번째 숫자 (또는 이전 결과)
    private var op: String? = null      // 선택한 연산자
    private var justEvaluated = false   // 방금 = 을 눌렀는지

    private lateinit var exprView: TextView
    private lateinit var inputView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left + 16, systemBars.top + 16, systemBars.right + 16, systemBars.bottom)
            insets
        }

        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)                          // ② 대화상자 객체 생성
            val inflater: LayoutInflater = layoutInflater                         // ③ 대화상자 레이아웃 설정
            val dialogView = inflater.inflate(R.layout.dialog_calculator, null)
            alertDialog.setView(dialogView)
            val dialog = alertDialog.show()                                       // ④ 대화상자 활성화
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent) // 둥근 모서리 보이게

            exprView = dialogView.findViewById(R.id.text_expression)
            inputView = dialogView.findViewById(R.id.text_input)
            resetAll()

            // 키패드의 모든 버튼에 같은 클릭 이벤트 연결
            val keypad: GridLayout = dialogView.findViewById(R.id.keypad)
            for (i in 0 until keypad.childCount) {
                val key = keypad.getChildAt(i) as Button
                key.setOnClickListener { press(key.text.toString()) }
            }

            // 닫기 버튼
            val close: Button = dialogView.findViewById(R.id.close)
            close.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    // 버튼 하나를 눌렀을 때
    private fun press(key: String) {
        when (key) {
            "AC" -> resetAll()
            "C" -> current = ""
            "←" -> if (current.isNotEmpty()) current = current.dropLast(1)

            "+", "-", "*", "/" -> {
                if (!justEvaluated && current.isNotEmpty()) {
                    val value = current.toDouble()
                    val a = first
                    val o = op
                    if (a != null && o != null) {
                        // 연속 계산: 50 - 6 + ... 처럼 앞의 계산을 먼저 처리
                        first = calculate(a, o, value) ?: return showError()
                    } else {
                        first = value
                    }
                }
                val a = first ?: return
                op = key
                current = ""
                justEvaluated = false
                exprView.text = "${format(a)} ${symbol(key)}"
            }

            "=" -> {
                val a = first ?: return
                val o = op ?: return
                if (current.isEmpty()) return
                val b = current.toDouble()
                val result = calculate(a, o, b) ?: return showError()
                exprView.text = "${format(a)} ${symbol(o)} ${format(b)} = ${format(result)}"
                first = result      // 결과를 다음 계산에 이어서 사용
                op = null
                justEvaluated = true
            }

            "." -> {
                if (justEvaluated) startFresh()
                if (!current.contains(".")) {
                    current = if (current.isEmpty()) "0." else "$current."
                }
            }

            else -> {   // 숫자
                if (justEvaluated) startFresh()
                if (current.length < 12) {
                    current = if (current == "0") key else current + key
                }
            }
        }
        updateInput()
    }

    private fun calculate(a: Double, o: String, b: Double): Double? {
        return when (o) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> if (b == 0.0) null else a / b
            else -> null
        }
    }

    private fun resetAll() {
        startFresh()
        updateInput()
    }

    private fun startFresh() {
        current = ""
        first = null
        op = null
        justEvaluated = false
        exprView.text = ""
    }

    private fun showError() {
        startFresh()
        exprView.text = "0으로 나눌 수 없습니다"
        updateInput()
    }

    private fun updateInput() {
        inputView.text = if (current.isEmpty()) "0" else current
    }

    // 화면에 보여줄 연산자 기호
    private fun symbol(o: String) = when (o) {
        "*" -> "×"
        "/" -> "÷"
        else -> o
    }

    // 44.0 → "44", 14.6666666666… → "14.666666667"
    private fun format(d: Double): String {
        return if (d % 1.0 == 0.0 && Math.abs(d) < 1e15) {
            d.toLong().toString()
        } else {
            BigDecimal.valueOf(d).setScale(9, RoundingMode.HALF_UP)
                .stripTrailingZeros().toPlainString()
        }
    }
}