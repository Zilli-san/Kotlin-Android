package com.androidbook.bookmarket

data class Book(
    val id: String,
    val title: String,
    val price: Int,
    val date: String,
    val author: String,
    val description: String,
    val category: String,
    val imageRes: Int
)

object BookData {
    val books = listOf(
        Book("BOOK1001", "Of Blood and Fire", 30000, "-", "Ryan Cahill",
            "도서 설명을 입력하세요.", "판타지", R.drawable.book1),
        Book("BOOK1002", "The Partition Project", 28000, "-", "Saadia Faruqi",
            "도서 설명을 입력하세요.", "청소년", R.drawable.book2),
        Book("BOOK1003", "Marlow", 32000, "-", "-",
            "도서 설명을 입력하세요.", "아동", R.drawable.book3),
        Book("BOOK1004", "Sulwe", 27000, "-", "Lupita Nyong'o",
            "도서 설명을 입력하세요.", "그림책", R.drawable.book4),
        Book("BOOK1005", "The Witcher: The Time of Contempt", 35000, "-", "Andrzej Sapkowski",
            "도서 설명을 입력하세요.", "판타지", R.drawable.book5),
        Book("BOOK1006", "Operation: Odyssey", 35000, "-", "-",
            "도서 설명을 입력하세요.", "-", R.drawable.book6)
    )
}