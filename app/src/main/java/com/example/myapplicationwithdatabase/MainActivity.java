package com.example.myapplicationwithdatabase;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnOne, btnTwo, btnThree;
    DBHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        btnOne = (Button) findViewById(R.id.btnOne);
        btnOne.setOnClickListener(this);
        btnTwo = (Button) findViewById(R.id.btnTwo);
        btnTwo.setOnClickListener(this);
        btnThree = (Button) findViewById(R.id.btnThree);
        btnThree.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
        database.delete(DBHelper.TABLE_CLASSMATES, null, null);
        dbHelper.addStartClassmates(database);
    }

    @Override
    public void onClick(View view) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor cursor;

        if (view.getId() == R.id.btnOne) {
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btnTwo) {
            String name = "Новый одноклассник";
            String currentTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

            contentValues.put(DBHelper.KEY_NAME, name);
            contentValues.put(DBHelper.KEY_TIME, currentTime);

            database.insert(DBHelper.TABLE_CLASSMATES, null, contentValues);
        } else if (view.getId() == R.id.btnThree) {
            cursor = database.query(DBHelper.TABLE_CLASSMATES, null, null, null, null, null, null);
            cursor.moveToLast();

            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int id = cursor.getInt(idIndex);

            contentValues.put(DBHelper.KEY_NAME, "Иванов Иван Иванович");

            int updCount = database.update(DBHelper.TABLE_CLASSMATES, contentValues, DBHelper.KEY_ID + "= ?", new String[]{String.valueOf(id)});

            cursor.close();
        }
        database.close();
        dbHelper.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}









