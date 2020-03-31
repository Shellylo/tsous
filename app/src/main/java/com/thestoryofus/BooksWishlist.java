package com.thestoryofus;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class BooksWishlist extends ListScreen implements BookOptionsDialogFragment.BookOptionsDialogListener, AdapterView.OnItemLongClickListener {

    private static final String TAG = "BooksWishlist";

    private boolean defaultIsRead;
    private String defaultOrderBy;
    private boolean defaultIsOrderAsc;

    private Button addBookBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_wishlist);

        this.defaultIsRead = false;
        this.defaultOrderBy = DatabaseContract.BookEntry.COL_NAME;
        this.defaultIsOrderAsc = true;

        this.list = findViewById(R.id.booksWishlist_booksList);
        adapterSetup();
        this.list.setOnItemLongClickListener(this);

        this.addBookBtn = findViewById(R.id.booksWishlist_btn_addBook);
        addBookButtonListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.updateList(); // Update list (in case a book was added)
    }

    /*
        Function initializes list adapter settings and sets it to be the adapter of book list.
        Book list attribute of the class should be initialized before calling this function.
        Input: None
        Output: None
     */
    private void adapterSetup() {
        Cursor cursor = this.sqlDB.getBooksReadFilterCursor(this.defaultIsRead, this.defaultOrderBy, this.defaultIsOrderAsc);

        String[] fromColumns = {DatabaseContract.BookEntry.COL_NAME, DatabaseContract.BookEntry.COL_AUTHOR};
        int[] toViews = {R.id.bookBrief_bookTitle, R.id.bookBrief_authorName};

        this.listAdapter = new SimpleCursorAdapter(this, R.layout.listitem_book_brief, cursor, fromColumns, toViews);
        this.list.setAdapter(this.listAdapter);
    }

    /*
        Function sets an updated cursor (using default settings) to the list adapter.
        Input: None
        Output: None
     */
    private void updateList() {
        this.listAdapter.changeCursor(this.sqlDB.getBooksReadFilterCursor(this.defaultIsRead, this.defaultOrderBy, this.defaultIsOrderAsc));
    }

    /*
        Listens to the button used to add new book. When clicked, changes activity to
        AddNewBookScreen.
        Input: None
        Output: None
     */
    private void addBookButtonListener() {
        this.addBookBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BooksWishlist.this, AddNewBookScreen.class);
                startActivity(intent);
            }
        });
    }

    /*
        Function updates IsRead attribute of book record to be true
        Input: ID of book record in the database(that should be marked as read)
        Output: None
     */
    @Override
    public void markBookAsRead(int bookID) {
        this.sqlDB.updateColIsRead(bookID, true);
        this.updateList();
    }

    /*
        Function removes book record from database and updates list.
        Input: ID of book record in the database (that should be removed)
        Output: None
     */
    @Override
    public void removeBook(int bookID) {
        this.sqlDB.removeBookByID(bookID);
        this.updateList();
    }

    /*
        Function will be called in case of a long click on an item from the books list in the
        screen.
        When an item from that list is clicked, a dialog should be opened. The function
        creates this dialog and displays it.

        Input: Parent AdapterView (the ListView of books)
                Specific long clicked view
                Position of specific view in the adapter
                Id of the specific view in database (a book record)
        Output: True if the callback consumed the long click, false otherwise.
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        BookOptionsDialogFragment bookDialog = BookOptionsDialogFragment.newInstance((int)id);
        bookDialog.show(getSupportFragmentManager(), "book_options_dialog_"+id);
        return true;
    }
}
