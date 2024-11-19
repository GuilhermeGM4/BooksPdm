package com.example.bookspdm.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.bookspdm.databinding.ActivityMainBinding
import com.example.bookspdm.model.Book

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    //Data source
    private val bookList: MutableList<Book> = mutableListOf()

    //Adapter
    private val bookAdapter: ArrayAdapter<String> by lazy {
//        val bookTitleList: MutableList<String> = mutableListOf()
//        bookList.forEach{ book -> bookTitleList.add(book.title)}
//        ArrayAdapter(this, android.R.layout.simple_list_item_1, bookTitleList)

        ArrayAdapter(this, android.R.layout.simple_list_item_1, bookList.run{
            val bookTitleList: MutableList<String> = mutableListOf()
            bookList.forEach{ book -> bookTitleList.add(book.title)}
            this.forEach{ bookTitleList.add(it.title) }
            bookTitleList
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        fillBookList()
        amb.booksLV.adapter = bookAdapter
    }

    private fun fillBookList(){
        for(index in 1..50){
            bookList.add(
                Book("Title $index", "ISBN$index", "Author $index", "Publisher $index", index, index*100)
            )
        }
    }
}