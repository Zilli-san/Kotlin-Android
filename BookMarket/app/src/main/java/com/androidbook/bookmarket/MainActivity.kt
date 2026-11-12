package com.androidbook.bookmarket

import android.os.Bundle
import android.util.Log
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
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var numOne = 1
        var numTwo = 3000000000
        var myByte: Byte = 1
        var myInt: Int = 20
        var myLong = 25 //명시적 long 지정

        Log.d("코틀린 : 정수 자료형 ","Int : " + numOne)
        Log.d("코틀린 : 정수 자료형 ", "Long : " +  numTwo)
        Log.d("코틀린 : 정수 자료형 ", "Byte : " + myByte)
        Log.d("코틀린 : 정수 자료형 ", "Int : " + myInt)
        Log.d("코틀린 : 정수 자료형 ", "Long : " + myLong)
    }
}