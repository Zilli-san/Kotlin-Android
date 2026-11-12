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

        //employee
        var myEmp = Employee(202201, "홍길순")

        //상속
        val myAnimal = Animal()
        val myDog = Dog()

        Log.d("코틀린 : 객체 지향 프로그래밍 ", "Animal 클래스: ${myAnimal.sound()}")
        Log.d("코틀린 : 객체 지향 프로그래밍 ", "Dog 클래스: ${myDog.sound()}")
    }
}

//상속할 클래스에 open 추가
open class Animal {
    open fun sound(): String {
        return "동물이 소리를 낸다"
    }
}

//상속받기
class Dog : Animal() {
    override fun sound(): String {
        return "멍멍"
    }
}