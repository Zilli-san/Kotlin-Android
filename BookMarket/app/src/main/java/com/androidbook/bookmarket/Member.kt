package com.androidbook.bookmarket
// oop
import android.util.Log

class Member {
    // 멤버 변수
    var name: String = ""
    var age: Int = 0
    var gender: Char = 'M'

    //멤버 함수
    fun printMemberInfo() {
        Log.d("코틀린 : 객체 지향 프로그래밍 ", "Member 이름 : $name")
        Log.d("코틀린 : 객체 지향 프로그래밍 ", "Member 나이 : $age")
        Log.d("코틀린 : 객체 지향 프로그래밍 ", "Member 성별 : $gender")
    }
}