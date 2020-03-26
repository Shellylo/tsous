package com.thestoryofus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class MySqliteDatabase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TheStoryOfUS.db";

    public MySqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + DatabaseContract.BookEntry.TABLE_NAME + "(" +
                        DatabaseContract.BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        DatabaseContract.BookEntry.COL_ADD_TIME + " TEXT NOT NULL, " +
                        DatabaseContract.BookEntry.COL_NAME + " TEXT NOT NULL, " +
                        DatabaseContract.BookEntry.COL_AUTHOR + " TEXT NOT NULL, " +
                        DatabaseContract.BookEntry.COL_PUBLISH_YEAR + " INTEGER NOT NULL," +
                        DatabaseContract.BookEntry.COL_GENRE + " INTEGER NOT NULL, " +
                        DatabaseContract.BookEntry.COL_LANGUAGE + " INTEGER NOT NULL, " +
                        DatabaseContract.BookEntry.COL_IS_AVAILABLE + " INTEGER NOT NULL, " +
                        DatabaseContract.BookEntry.COL_IS_READ + " INTEGER NOT NULL" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "  + DatabaseContract.BookEntry.TABLE_NAME);
        onCreate(db);
    }

    public Cursor select(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        SQLiteDatabase db = getReadableDatabase();

        /*
         * The table to query from
         * Select columns to return (null for all)
         * WHERE clause columns (SQL Injection safe)
         * WHERE clause values (SQL Injection safe)
         * Group rows
         * Filter by row groups
         * Sort Order
         */
        return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public Cursor getBooksReadFilterCursor(boolean isRead) {
        // Which columns from the database will be used (selected)
        String[] projection = {DatabaseContract.BookEntry._ID,
                DatabaseContract.BookEntry.COL_ADD_TIME,
                DatabaseContract.BookEntry.COL_NAME,
                DatabaseContract.BookEntry.COL_AUTHOR,
                DatabaseContract.BookEntry.COL_PUBLISH_YEAR,
                DatabaseContract.BookEntry.COL_GENRE,
                DatabaseContract.BookEntry.COL_LANGUAGE,
                DatabaseContract.BookEntry.COL_IS_AVAILABLE};

        // Filter results (WHERE clause)
        String selection = DatabaseContract.BookEntry.COL_IS_READ + " = ?";
        String[] selectionArgs = {isRead ? "1" : "0"};

        // How the results will be sorted (by author name)
        String sortOrder = DatabaseContract.BookEntry.COL_AUTHOR + " ASC";

        return select(DatabaseContract.BookEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    public ArrayList<ArcBook> getBooksReadFilter(boolean isRead) {
        ArrayList<ArcBook> booksList = new ArrayList<>();

        Cursor cursor = getBooksReadFilterCursor(isRead);
        // Cursor Starts at -1, so moveToNext() method updates cursor's "read position" to be the first entry.
        // moveToNext() also returns false if the cursor is past the last entry, or true otherwise.
        // To get columns values, use get<Type>() cursor's methods, which receive index of the column.
        // Index of a column can be received using getColumnIndex() / getColumnsIndexOrThrow() methods of the cursor.
        // Close() releases cursor resources.
        while (cursor.moveToNext()) {
            booksList.add(new ArcBook(
                    cursor.getInt(cursor.getColumnIndex(DatabaseContract.BookEntry._ID)),
                    new Book(
                            cursor.getString(cursor.getColumnIndex(DatabaseContract.BookEntry.COL_NAME)),
                            cursor.getString(cursor.getColumnIndex(DatabaseContract.BookEntry.COL_AUTHOR)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseContract.BookEntry.COL_LANGUAGE)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseContract.BookEntry.COL_PUBLISH_YEAR)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseContract.BookEntry.COL_GENRE)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseContract.BookEntry.COL_IS_AVAILABLE)) == 1 ? true : false,
                            isRead
                    ),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.BookEntry.COL_ADD_TIME))
            ));
        }
        cursor.close();

        return booksList;
    }

    public int insertBook(ArcBook arcBook) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create new map of values, where column names are keys
        ContentValues values = new ContentValues();
        Book book = arcBook.getBook();
        values.put(DatabaseContract.BookEntry.COL_ADD_TIME, arcBook.getAddTime());
        values.put(DatabaseContract.BookEntry.COL_NAME, book.getName());
        values.put(DatabaseContract.BookEntry.COL_AUTHOR, book.getAuthor());
        values.put(DatabaseContract.BookEntry.COL_PUBLISH_YEAR, book.getPublishYear());
        values.put(DatabaseContract.BookEntry.COL_GENRE, book.getGenre());
        values.put(DatabaseContract.BookEntry.COL_LANGUAGE, book.getLanguage());
        values.put(DatabaseContract.BookEntry.COL_IS_AVAILABLE, book.isAvailable() ? 1 : 0);
        values.put(DatabaseContract.BookEntry.COL_IS_READ, book.isRead() ? 1 : 0);

        return (int)db.insert(DatabaseContract.BookEntry.TABLE_NAME, null, values);
    }

    public void removeBookByID(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = DatabaseContract.BookEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.delete(DatabaseContract.BookEntry.TABLE_NAME, selection, selectionArgs);
    }

    public Cursor getBookCursorByID(int id) {
        String[] projection = {DatabaseContract.BookEntry.COL_ADD_TIME,
                DatabaseContract.BookEntry.COL_NAME,
                DatabaseContract.BookEntry.COL_AUTHOR,
                DatabaseContract.BookEntry.COL_PUBLISH_YEAR,
                DatabaseContract.BookEntry.COL_GENRE,
                DatabaseContract.BookEntry.COL_LANGUAGE,
                DatabaseContract.BookEntry.COL_IS_AVAILABLE,
                DatabaseContract.BookEntry.COL_IS_READ};

        String selection = DatabaseContract.BookEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        return select(DatabaseContract.BookEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null,
                "LIMIT 1");
    }

    public ArcBook getBookByID(int id) {
        ArcBook book = null;
        Cursor cursor = getBookCursorByID(id);

        if (cursor.moveToNext()) {
            book = new ArcBook(
                    id,
                    new Book(
                            cursor.getString(cursor.getColumnIndex(DatabaseContract.BookEntry.COL_NAME)),
                            cursor.getString(cursor.getColumnIndex(DatabaseContract.BookEntry.COL_AUTHOR)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseContract.BookEntry.COL_LANGUAGE)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseContract.BookEntry.COL_PUBLISH_YEAR)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseContract.BookEntry.COL_GENRE)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseContract.BookEntry.COL_IS_AVAILABLE)) == 1 ? true : false,
                            cursor.getInt(cursor.getColumnIndex(DatabaseContract.BookEntry.COL_IS_READ)) == 1 ? true : false
                            ),
                    cursor.getString(cursor.getColumnIndex(DatabaseContract.BookEntry.COL_ADD_TIME))
            );
        }
        return book;
    }

}

