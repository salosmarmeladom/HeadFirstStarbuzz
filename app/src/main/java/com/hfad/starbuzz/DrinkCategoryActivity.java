package com.hfad.starbuzz;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;

public class DrinkCategoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);
        //создание адаптера, заполняющего ListView макета
        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, Drink.drinks);
        ListView listDrinks = findViewById(R.id.list_drinks);
        listDrinks.setAdapter(listAdapter);

        //создание слушателя
        AdapterView.OnItemClickListener itemClickListener =
                (listDrinks1, itemView, position, id) -> {
                    //передача напитка, выбранного пользователем, в DrinkActivity
                    Intent intent = new Intent(DrinkCategoryActivity.this,
                            DrinkActivity.class);
                    intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int) id);
                    startActivity(intent);
                };
        listDrinks.setOnItemClickListener(itemClickListener);
    }
}
