package com.androidbook.bookmarket

import android.os.Bundle
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BookListActivity : AppCompatActivity() {

    data class Book(val title: String, val price: String, val imageRes: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_book_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val books = listOf(
            Book("Of Blood and Fire", "정가: 30,000원", R.drawable.book1),
            Book("The Partition Project", "정가: 28,000원", R.drawable.book2),
            Book("Marlow", "정가: 32,000원", R.drawable.book3),
            Book("Sulwe", "정가: 27,000원", R.drawable.book4),
            Book("The Witcher: The Time of Contempt", "정가: 35,000원", R.drawable.book5),
            Book("Operation: Odyssey", "정가: 35,000원", R.drawable.book6)
        )

        val bookGrid = findViewById<GridLayout>(R.id.bookGrid)

        for (book in books) {
            val item = layoutInflater.inflate(R.layout.item_book, bookGrid, false)

            item.findViewById<ImageView>(R.id.bookImage).setImageResource(book.imageRes)
            item.findViewById<TextView>(R.id.bookTitle).text = book.title
            item.findViewById<TextView>(R.id.bookPrice).text = book.price

            // 도서를 클릭하면 도서명을 토스트로 출력
            item.setOnClickListener {
                Toast.makeText(applicationContext, book.title, Toast.LENGTH_SHORT).show()
            }

            // 2열에 같은 너비로 배치
            val params = GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED),
                GridLayout.spec(GridLayout.UNDEFINED, 1f)
            )
            params.width = 0
            item.layoutParams = params

            bookGrid.addView(item)
        }
    }
}