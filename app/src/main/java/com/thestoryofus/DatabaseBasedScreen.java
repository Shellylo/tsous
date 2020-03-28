package com.thestoryofus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class DatabaseBasedScreen extends AppCompatActivity {
    protected MySqliteDatabase sqlDB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sqlDB = new MySqliteDatabase(this);
    }

    @Override
    protected void onDestroy() {
        this.sqlDB.close();
        super.onDestroy();
    }
}
