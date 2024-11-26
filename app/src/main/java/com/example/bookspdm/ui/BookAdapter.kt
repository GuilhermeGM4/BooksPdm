package com.example.bookspdm.ui

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.bookspdm.R
import com.example.bookspdm.databinding.TileBookBinding
import com.example.bookspdm.model.Book

class BookAdapter(
    context: Context,
    private val bookList: MutableList<Book>
): ArrayAdapter<Book>(context, R.layout.tile_book, bookList) {

    private data class BookTileHolder(
        val titleTV: TextView,
        val firstAuthorTV: TextView,
        val editionTV: TextView
    )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        lateinit var tbb: TileBookBinding

        //Pegar livro que vai ser usado para preencher a celula
        val book = bookList[position]

        //Verificando se a celula ja foi inflada ou nao.
        var bookTile = convertView
        if(book == null) {
            //Se nao foi, infla uma nova celula
            tbb = TileBookBinding.inflate(
                context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            )
//            bookTile = (context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater)
//                .inflate(R.layout.tile_book, parent, false)

            bookTile = tbb.root

            //Criar um holder para a nova celula
            val newBookTileHolder = BookTileHolder(
//                bookTile.findViewById(R.id.titleTV),
//                bookTile.findViewById(R.id.firstAuthorTV),
//                bookTile.findViewById(R.id.editionTV)
                tbb.titleTV,
                tbb.firstAuthorTV,
                tbb.editionTV
            )

            // Associar holder a nova celula
            bookTile.tag = newBookTileHolder
        }

        //Preenche os valores da celula com um novo livro
        val holder = bookTile?.tag as BookTileHolder
        holder.let {
            it.titleTV.text = book.title
            it.firstAuthorTV.text = book.firstAuthor
            it.editionTV.text = book.edition.toString()
        }
//        bookTile?.findViewById<TextView>(R.id.titleTV)?.text = book.title
//        bookTile?.findViewById<TextView>(R.id.firstAuthorTV)?.text = book.firstAuthor
//        bookTile?.findViewById<TextView>(R.id.editionTV)?.text = book.edition.toString()

        //Retorna a celula preenchida
        return bookTile!!
    }
}