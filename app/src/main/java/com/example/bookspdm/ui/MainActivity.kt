package com.example.bookspdm.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.bookspdm.R
import com.example.bookspdm.controller.MainController
import com.example.bookspdm.databinding.ActivityMainBinding
import com.example.bookspdm.model.Book
import com.example.bookspdm.model.Constant
import com.example.bookspdm.model.Constant.BOOK
import com.example.bookspdm.model.Constant.VIEW_MODE
class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    //Data source
    private val bookList: MutableList<Book> = mutableListOf()

    //Adapter
    private val bookAdapter: BookAdapter by lazy {
//        val bookTitleList: MutableList<String> = mutableListOf()
//        bookList.forEach{ book -> bookTitleList.add(book.title)}
//        ArrayAdapter(this, android.R.layout.simple_list_item_1, bookTitleList)

//        ArrayAdapter(this, android.R.layout.simple_list_item_1, bookList.run{
//            val bookTitleList: MutableList<String> = mutableListOf()
//            bookList.forEach{ book -> bookTitleList.add(book.title)}
//            this.forEach{ bookTitleList.add(it.title) }
//            bookTitleList
//        })
        BookAdapter(this, bookList)
    }

    //Controller
    private val mainController: MainController by lazy {
        MainController(this)
    }

    private lateinit var barl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        barl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
            if(result.resultCode == RESULT_OK){
                val book = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra(Constant.BOOK, Book::class.java)
                }
                else{
                    result.data?.getParcelableExtra<Book>(Constant.BOOK)
                }

                book?.let { receivedBook ->
                    val position = bookList.indexOfFirst { it.isbn == receivedBook.isbn }
                    if(position == -1){
                        bookList.add(receivedBook)
                        mainController.insertBook(receivedBook)
                    }
                    else{
                        bookList[position] = receivedBook
                        mainController.modifyBook(receivedBook)
                    }
//                    bookAdapter.add(it.title)
                    bookAdapter.notifyDataSetChanged()
                }
            }
        }

        amb.toolbarIn.toolbar.let {
            it.subtitle = getString(R.string.book_list)
            setSupportActionBar(it)
        }

        fillBookList()
        amb.booksLV.adapter = bookAdapter
        amb.booksLV.setOnItemClickListener { _, _, position, _ ->
            val book = bookList[position]
            Intent(this, BookActivity::class.java).apply{
                putExtra(BOOK, bookList[position])
                putExtra(VIEW_MODE, true)
                startActivity(this)
            }
        }

        registerForContextMenu(amb.booksLV)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId){
        R.id.addBookMI -> {
            //Abrir tela para adicionar novo livro
            barl.launch(Intent(this, BookActivity::class.java))
            true
        }
        else -> false
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) = menuInflater.inflate(R.menu.context_menu_main, menu)

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterContextMenuInfo).position
        return when (item.itemId){
            R.id.editBookMI -> {
                //Chamar tela de edicao de livro
                Intent(this, BookActivity::class.java).apply {
                    putExtra(BOOK, bookList[position])
                    barl.launch(this)
                }
                true
            }
            R.id.removeBookMI -> {
                //Remover livro da lista
                mainController.removeBook(bookList[position].isbn)
                bookList.removeAt(position)
                bookAdapter.notifyDataSetChanged()
                true
            }
            else -> false
        }
    }

    private fun fillBookList(){
//        for(index in 1..50){
//            bookList.add(
//                Book(
//                    "Title $index",
//                    "ISBN$index",
//                    "Author $index",
//                    "Publisher $index",
//                    index,
//                    index*100
//                )
//            )
//        }
        Thread {
            bookList.clear()
            bookList.addAll(mainController.getBooks())
            bookAdapter.notifyDataSetChanged()
        }.start()
    }
}