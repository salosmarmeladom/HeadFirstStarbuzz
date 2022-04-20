package com.hfad.starbuzz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class TopLevelActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor favoritesCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        setupOptionsListView();
        setupFavoritesListView();
    }

    private void setupOptionsListView() {
        //создание слушателя
        AdapterView.OnItemClickListener itemClickListener =
                (listView, itemView, position, id) -> {
                    if (position == 0) {
                        Intent intent = new Intent(TopLevelActivity.this,
                                DrinkCategoryActivity.class);

                        startActivity(intent);
                    }
                };
        //прикрепляет слушателя к списку
        ListView listView = findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
    }

    //заполнение списка любимых напитков из базы данных
    private void setupFavoritesListView() {
        ListView listFavorites = findViewById(R.id.list_favorites);
        try {
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            db = starbuzzDatabaseHelper.getReadableDatabase();
            favoritesCursor = db.query("DRINK", new String[] {"_id", "NAME"}, "FAVORITE = 1", null, null, null, null);
            CursorAdapter favoriteAdapter = new SimpleCursorAdapter(TopLevelActivity.this, android.R.layout.simple_list_item_1, favoritesCursor, new String[] {"NAME"}, new int[]{android.R.id.text1}, 0);
            listFavorites.setAdapter(favoriteAdapter);
        } catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavaliable", Toast.LENGTH_SHORT);
            toast.show();
        }
        //переход к DrinkActivity при выборе напитка
        listFavorites.setOnItemClickListener((adapterView, v, position, id) -> {
            Intent intent = new Intent(TopLevelActivity.this, DrinkActivity.class);
            intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int)id);
            startActivity(intent);
        });
    }

    @Override
    public void onRestart() {
    super.onRestart();
    Cursor newCursor = db.query("DRINK", new String[]{"_id", "NAME"}, "FAVORITE = 1", null, null, null, null);
    ListView listFavorites = findViewById(R.id.list_favorites);
    CursorAdapter adapter = (CursorAdapter) listFavorites.getAdapter();
    adapter.changeCursor(newCursor);
    }

    //закрытие курсора и базы данных
    @Override
    public void onDestroy(){
     super.onDestroy();
     favoritesCursor.close();
     db.close();
    }
}