package com.thestoryofus;

import android.provider.BaseColumns;

public class DatabaseContract {

    private DatabaseContract() {}

    public static class BookEntry implements BaseColumns {
        public static final String TABLE_NAME = "BOOKS";
        public static final String COL_ADD_TIME = "ADD_TIME";
        public static final String COL_NAME = "NAME";
        public static final String COL_AUTHOR = "AUTHOR";
        public static final String COL_PUBLISH_YEAR = "PUBLISH_YEAR";
        public static final String COL_GENRE = "GENRE";
        public static final String COL_LANGUAGE = "LANGUAGE";
        public static final String COL_IS_AVAILABLE = "IS_AVAILABLE";
        public static final String COL_IS_READ = "IS_READ";
    }
}
