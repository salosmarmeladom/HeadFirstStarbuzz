package com.hfad.starbuzz;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ListView;

public class TopLevelActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        AdapterView.OnItemClickListener itemClickListener =
                (listView, itemView, position, id) -> {
                    if (position == 0) {
                        Intent intent = new Intent(TopLevelActivity.this,
                                DrinkCategoryActivity.class);

                    startActivity(intent);
                    }
                };
        //Добавление слушателя к ListView
        ListView listView = findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
    }
}