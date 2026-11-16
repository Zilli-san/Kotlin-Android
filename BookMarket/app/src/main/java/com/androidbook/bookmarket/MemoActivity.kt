package com.androidbook.bookmarket

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MemoActivity : AppCompatActivity() {

    private lateinit var dbHelper: MemoDbHelper
    private lateinit var adapter: MemoAdapter
    private lateinit var emptyText: TextView
    private var keyword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_memo)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, 0, bars.right, bars.bottom)
            toolbar.setPadding(0, bars.top, 0, 0)
            insets
        }
        setSupportActionBar(toolbar)
        supportActionBar?.title = "메모장"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dbHelper = MemoDbHelper(this)
        emptyText = findViewById(R.id.memo_empty)

        val recyclerView: RecyclerView = findViewById(R.id.memo_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MemoAdapter()
        recyclerView.adapter = adapter

        // 등록
        findViewById<Button>(R.id.btn_add).setOnClickListener {
            showMemoDialog(null)
        }

        // 수정
        findViewById<Button>(R.id.btn_edit).setOnClickListener {
            val memo = adapter.selectedMemo()
            if (memo == null) {
                Toast.makeText(applicationContext, "수정할 메모를 선택하세요.", Toast.LENGTH_SHORT).show()
            } else {
                showMemoDialog(memo)
            }
        }

        // 삭제
        findViewById<Button>(R.id.btn_delete).setOnClickListener {
            val memo = adapter.selectedMemo()
            if (memo == null) {
                Toast.makeText(applicationContext, "삭제할 메모를 선택하세요.", Toast.LENGTH_SHORT).show()
            } else {
                AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("메모장 삭제")
                    .setMessage("선택한 메모를 삭제하겠습니까?")
                    .setNegativeButton("아니요", null)
                    .setPositiveButton("예") { _, _ ->
                        dbHelper.delete(memo.id)
                        loadMemos()
                        Toast.makeText(applicationContext, "메모를 삭제했습니다.", Toast.LENGTH_SHORT).show()
                    }
                    .show()
            }
        }

        loadMemos()
    }

    // 데이터베이스에서 읽어 목록 갱신
    private fun loadMemos() {
        val memos = dbHelper.getMemos(keyword)
        adapter.submit(memos)
        emptyText.visibility = if (memos.isEmpty()) View.VISIBLE else View.GONE
    }

    // 등록(memo == null) / 수정(memo != null) 대화상자
    private fun showMemoDialog(memo: Memo?) {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_memo, null)
        builder.setView(dialogView)
        val dialog = builder.show()

        val titleInput: EditText = dialogView.findViewById(R.id.memo_title_input)
        val contentInput: EditText = dialogView.findViewById(R.id.memo_content_input)
        val okButton: Button = dialogView.findViewById(R.id.dialog_ok)
        val cancelButton: Button = dialogView.findViewById(R.id.dialog_cancel)

        if (memo == null) {
            okButton.text = "등록"
        } else {
            okButton.text = "수정"
            titleInput.setText(memo.title)
            contentInput.setText(memo.content)
        }

        okButton.setOnClickListener {
            val title = titleInput.text.toString().trim()
            val content = contentInput.text.toString().trim()
            if (title.isEmpty()) {
                Toast.makeText(applicationContext, "제목을 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(Date())

            if (memo == null) {
                dbHelper.insert(title, content, today)
                Toast.makeText(applicationContext, "메모가 등록되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                dbHelper.update(memo.id, title, content, today)
                Toast.makeText(applicationContext, "메모가 수정되었습니다.", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
            loadMemos()
        }

        cancelButton.setOnClickListener { dialog.dismiss() }
    }

    // 앱 바의 검색
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_memo, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.queryHint = "검색어 입력"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                keyword = query ?: ""
                loadMemos()
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {   // 검색어를 지우면 전체 목록
                    keyword = ""
                    loadMemos()
                }
                return true
            }
        })
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}