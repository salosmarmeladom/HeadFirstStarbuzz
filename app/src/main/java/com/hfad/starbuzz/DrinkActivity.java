package com.hfad.starbuzz;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends Activity {

    public static final String EXTRA_DRINKID = "drinkId";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        //получение напитка из данных интента
        int drinkId = (Integer)getIntent().getExtras().get(EXTRA_DRINKID);

        //создание курсора базы данных
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try {
            SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("DRINK", new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"}, "_id = ?", new String[]{Integer.toString(drinkId)}, null, null, null);
            //переход к первой записи в курсоре
            if (cursor.moveToFirst()) {

                //получение данных напитка
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);

                //название напитка
                TextView name = findViewById(R.id.name);
                name.setText(nameText);

                //описание напитка
                TextView description = findViewById(R.id.description);
                description.setText(descriptionText);

                //изображение напитка
                ImageView photo = findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(descriptionText);
            }
            cursor.close();
            db.close();
        } catch(SQLException e) {
            Toast toast = Toast.makeText(this, "DatabaseInavaliable", Toast.LENGTH_SHORT);
            toast.show();
        }


    }




}
