package com.example.demo01;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class HikeDetails extends AppCompatActivity {

    //view
    private TextView tv_hikeName,tv_hikeLocation,tv_hikeDate,tv_hikeLength,tv_hikeTime,tv_hikeDifficultyLevel,tv_hikeStopPoint,tv_hikeDescription;
    private RadioGroup radioGroupParking;
    private String id;
    //db halper
    private DbHelper dbHelper;


    //test
    private Observation observationdb;
    private ObservationAdapter observationAdapter;
    private RecyclerView observationRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_details);
        // init dbHelper hike
        dbHelper = new DbHelper(this);
        //init db observation
        observationdb = new Observation(this);
        observationRv = findViewById(R.id.observationRv);
        // loadDataObsById (observation)
        loadDataObsById();
        //get data from intents
        Intent intent = getIntent();
        id = intent.getStringExtra("Id");
        //init view hike
        tv_hikeName = findViewById(R.id.tv_hikeName);
        tv_hikeLocation = findViewById(R.id.tv_hikeLocation);
        tv_hikeDate = findViewById(R.id.tv_hikeDate);
        tv_hikeLength = findViewById(R.id.tv_hikeLength);
        tv_hikeTime = findViewById(R.id.tv_hikeTime);
        tv_hikeDifficultyLevel = findViewById(R.id.tv_hikeDifficultyLevel);
        tv_hikeStopPoint = findViewById(R.id.tv_hikeStopPoint);
        radioGroupParking = findViewById(R.id.radioGroupParking);
        tv_hikeDescription = findViewById(R.id.tv_hikeDescription);
        //loadDatabyId Hike
        loadDataById();
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadDataById() {

        // lấy dữ liệu từ cơ sở dữ liệu
        //truy vấn tìm dữ liệu theo id
        String selectQuery = "SELECT * FROM " + Hike.TABLE_NAME + " WHERE " + Hike.C_ID + " = '" + id + "'";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                //get data hike
                String hikeName = cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeName));
                String hikeLocation = cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeLocation));
                String hikeDate = cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeDate));
                String hikeLength = cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeLength));
                String hikeTime = cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeTime));
                String hikeStopPoint = cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeStopPoint));
                String hikeDifficultyLevel = cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeDifficultyLevel));
                boolean groupParking = cursor.getInt(cursor.getColumnIndexOrThrow(Hike.C_GroupParking)) == 1;
                String hikeDescription = cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeDescription));
                //set data hike
                tv_hikeName.setText(hikeName);
                tv_hikeLocation.setText(hikeLocation);
                tv_hikeDate.setText(hikeDate);
                tv_hikeLength.setText(hikeLength);
                tv_hikeTime.setText(hikeTime);
                tv_hikeStopPoint.setText(hikeStopPoint);
                tv_hikeDifficultyLevel.setText(hikeDifficultyLevel);
                if (groupParking) {
                    radioGroupParking.check(R.id.radioBtnYes);
                } else {
                    radioGroupParking.check(R.id.radioBtnNo);
                }
                tv_hikeDescription.setText(hikeDescription);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }

    private void loadDataObsById() {
        // Các khai báo khác
        observationAdapter = new ObservationAdapter(this, observationdb.getAllObservationsForHike(id));
        observationRv.setAdapter(observationAdapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadDataObsById(); //refresh data
    }

}