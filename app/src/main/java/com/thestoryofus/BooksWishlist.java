package com.thestoryofus;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class BooksWishlist extends ListScreen {

    private static final String TAG = "BooksWishlist";

    private ListView viewBooksList;
    private SimpleCursorAdapter viewBooksListAdapter;
    private Button addBookBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_wishlist);

        this.viewBooksList = findViewById(R.id.booksWishlist_booksList); // Get list view, adapter will be set later on

        this.addBookBtn = findViewById(R.id.booksWishlist_btn_addBook);
        addBookButtonListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapterSetup();
    }

    private void adapterSetup() {
        Cursor booksCursor = this.sqlDB.getBooksReadFilterCursor(true);

        String[] fromColumns = {DatabaseContract.BookEntry.COL_NAME, DatabaseContract.BookEntry.COL_AUTHOR};
        int[] toViews = {R.id.bookBrief_bookTitle, R.id.bookBrief_authorName};

        this.viewBooksListAdapter = new SimpleCursorAdapter(this, R.layout.listitem_book_brief, booksCursor, fromColumns, toViews);
        this.viewBooksList.setAdapter(this.viewBooksListAdapter);
    }

    private void addBookButtonListener() {
        this.addBookBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BooksWishlist.this, AddNewBookScreen.class);
                startActivity(intent);
            }
        });
    }

}
