package com.androidbook.bookmarket

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class Memo(
    val id: Long,
    val title: String,
    val content: String,
    val date: String
)

// SQLite 데이터베이스 관리 클래스 (10장 05절)
class MemoDbHelper(context: Context) : SQLiteOpenHelper(context, "memo.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE memos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "title TEXT, content TEXT, date TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS memos")
        onCreate(db)
    }

    // 삽입
    fun insert(title: String, content: String, date: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("content", content)
            put("date", date)
        }
        db.insert("memos", null, values)
        db.close()
    }

    // 수정
    fun update(id: Long, title: String, content: String, date: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("content", content)
            put("date", date)
        }
        db.update("memos", values, "id = ?", arrayOf(id.toString()))
        db.close()
    }

    // 삭제
    fun delete(id: Long) {
        val db = writableDatabase
        db.delete("memos", "id = ?", arrayOf(id.toString()))
        db.close()
    }

    // 조회 (keyword가 있으면 검색)
    fun getMemos(keyword: String = ""): List<Memo> {
        val list = mutableListOf<Memo>()
        val db = readableDatabase
        val cursor = if (keyword.isBlank()) {
            db.query("memos", null, null, null, null, null, "id DESC")
        } else {
            db.query(
                "memos", null,
                "title LIKE ? OR content LIKE ?",
                arrayOf("%$keyword%", "%$keyword%"),
                null, null, "id DESC"
            )
        }
        while (cursor.moveToNext()) {
            list.add(
                Memo(
                    cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("title")),
                    cursor.getString(cursor.getColumnIndexOrThrow("content")),
                    cursor.getString(cursor.getColumnIndexOrThrow("date"))
                )
            )
        }
        cursor.close()
        db.close()
        return list
    }
}