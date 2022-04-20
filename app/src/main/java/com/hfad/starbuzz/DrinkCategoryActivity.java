package com.hfad.starbuzz;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DrinkCategoryActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);
        ListView listDrinks = findViewById(R.id.list_drinks);
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try{
            db = starbuzzDatabaseHelper.getReadableDatabase();
            cursor = db.query("DRINK", new String[]{"_id", "NAME"}, null, null, null, null, null);
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{"NAME"}, new int[]{android.R.id.text1}, 0);
            listDrinks.setAdapter(listAdapter);
        } catch (SQLException e){
            Toast toast = Toast.makeText(this, "Database unavaliable", Toast.LENGTH_SHORT);
            toast.show();
        }

        //создание слушателя
        AdapterView.OnItemClickListener itemClickListener =
                (listDrinks1, itemView, position, id) -> {
                    //передача напитка, выбранного пользователем, в DrinkActivity
                    Intent intent = new Intent(DrinkCategoryActivity.this,
                            DrinkActivity.class);
                    intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int) id);
                    startActivity(intent);
                };
        //слушатель связывается со списковым представлением
        listDrinks.setOnItemClickListener(itemClickListener);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
