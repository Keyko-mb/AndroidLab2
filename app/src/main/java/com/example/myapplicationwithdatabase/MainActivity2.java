package com.example.myapplicationwithdatabase;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DBHelper(this);
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_CLASSMATES, null, null, null, null, null, null);

        TextView textViewData = findViewById(R.id.textViewData);

        StringBuilder data = new StringBuilder();

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TIME));

                data.append("ID: ").append(id).append("\nФИО: ").append(name).append("\nВремя: ").append(time).append("\n\n");
            } while (cursor.moveToNext());
        } else {
            data.append("No data available");
        }

        textViewData.setText(data.toString());

        cursor.close();
        database.close();
    }
}