package com.androidbook.bookmarket

data class CartItem(
    val book: Book,
    var quantity: Int = 1,
    var checked: Boolean = true
) {
    val subtotal: Int
        get() = book.price * quantity
}

// 앱 전체에서 하나만 존재하는 장바구니
object CartManager {
    val items = mutableListOf<CartItem>()

    fun add(book: Book) {
        val found = items.find { it.book.id == book.id }
        if (found != null) found.quantity++ else items.add(CartItem(book))
    }

    fun count(): Int = items.sumOf { it.quantity }
    fun checkedItems(): List<CartItem> = items.filter { it.checked }
    fun checkedTotal(): Int = checkedItems().sumOf { it.subtotal }
    fun clear() = items.clear()
}