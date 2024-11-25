package com.example.bookspdm.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bookspdm.R
import com.example.bookspdm.databinding.ActivityBookBinding
import com.example.bookspdm.model.Book
import com.example.bookspdm.model.Constant

class BookActivity : AppCompatActivity() {
    private val abb: ActivityBookBinding by lazy {
        ActivityBookBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(abb.root)

        abb.toolbarIn.toolbar.let {
            it.subtitle = "New Book"
            setSupportActionBar(it)
        }

        abb.run {
            SaveBt.setOnClickListener {
                val newBook = Book(
                    titleET.text.toString(),
                    isbnET.text.toString(),
                    firstAuthorET.text.toString(),
                    publisherET.text.toString(),
                    editionET.text.toString().toInt(),
                    pagesET.text.toString().toInt()
                ).let { book ->
                    Intent().apply {
                        putExtra(Constant.BOOK, book)
                        setResult(RESULT_OK, this)
                        finish()
                    }
                }
            }
        }
    }
}