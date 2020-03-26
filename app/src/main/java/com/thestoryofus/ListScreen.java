package com.thestoryofus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ListScreen extends AppCompatActivity {

    protected MySqliteDatabase sqlDB;

    @Override
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
