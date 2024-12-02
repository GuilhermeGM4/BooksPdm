package com.example.bookspdm.model

interface BookDao {
    fun createBook(book: Book): Long
    fun retrieveBook(isbn: String): Book
    fun retrieveBooks(): MutableList<Book>
    fun updateBook(book: Book): Int
    fun deleteBook(isbn: String): Int
}