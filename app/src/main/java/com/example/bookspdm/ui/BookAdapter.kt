package com.example.bookspdm.ui

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.bookspdm.R
import com.example.bookspdm.model.Book

class BookAdapter(
    context: Context,
    private val bookList: MutableList<Book>
): ArrayAdapter<Book>(context, R.layout.tile_book, bookList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //Pegar livro que vai ser usado para preencher a celula
        val book = bookList[position]

        //Verificando se a celula ja foi inflada ou nao.
        var bookTile = convertView
        if(book == null) {
            //Se nao foi, infla uma nova celula
            (context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.tile_book, parent, false)
        }

        //Preenche os valores da celula com um novo livro
        bookTile.findViewById<TextView>(R.id.titleTV)?.text = book.title
        bookTile.findViewById<TextView>(R.id.firstAuthorTV)?.text = book.firstAuthor
        bookTile.findViewById<TextView>(R.id.editionTV)?.text = book.edition.toString()

        //Retorna a celula preenchida
        return bookTile!!
    }
}