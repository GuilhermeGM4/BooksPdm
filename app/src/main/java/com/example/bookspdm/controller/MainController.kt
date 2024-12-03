package com.example.bookspdm.controller

import com.example.bookspdm.model.Book
import com.example.bookspdm.model.BookDao
import com.example.bookspdm.model.BookSqliteImpl
import com.example.bookspdm.ui.MainActivity

class MainController(mainActivity: MainActivity) {
    private val bookDao: BookDao = BookSqliteImpl(mainActivity)

    fun insertBook(book: Book) = bookDao.createBook(book)
    fun getBook(isbn: String) = bookDao.retrieveBook(isbn)
    fun getBooks() = bookDao.retrieveBooks()
    fun modifyBook(book: Book) = bookDao.updateBook(book)
    fun removeBook(isbn: String) = bookDao.deleteBook(isbn)
}