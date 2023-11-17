package com.example.demo01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ObservationDetails extends AppCompatActivity {

    private TextView observationDetailsTitle, observationInputDetails, observationTimeInputDetails, additionalCommentsInputDetails;
    private Button btnBackFromDetails;
    //db Observation
    private Observation observationdb;
    private String obsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_details);
        // Khởi tạo đối tượng obserationdb;
        observationdb = new Observation(this);
        //get data from intents
        Intent intent = getIntent();
        obsId = intent.getStringExtra("obsId");
        //init view observation
        observationInputDetails = findViewById(R.id.observationInputDetails);
        observationTimeInputDetails = findViewById(R.id.observationTimeInputDetails);
        additionalCommentsInputDetails = findViewById(R.id.additionalCommentsInputDetails);
        loadDataById();
        // Xử lý sự kiện khi nhấn nút Back
        btnBackFromDetails = findViewById(R.id.btnBackFromDetails);
        btnBackFromDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng Activity và quay trở lại màn hình trước đó
            }
        });
    }

    private void loadDataById() {
        // lấy dữ liệu từ cơ sở dữ liệu
        //truy vấn tìm dữ liệu theo id
        String selectQuery = "SELECT * FROM " + Observation.TABLE_NAME + " WHERE " + Observation.C_OBSERVATION_ID + " = '" + obsId + "'";

        SQLiteDatabase db = observationdb.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                //get data observation
                String hikeId = cursor.getString(cursor.getColumnIndexOrThrow(Observation.C_ID));
                String observationName = cursor.getString(cursor.getColumnIndexOrThrow(Observation.C_OBSERVATION));
                String observationTime = cursor.getString(cursor.getColumnIndexOrThrow(Observation.C_OBSERVATION_TIME));
                String additionalComments = cursor.getString(cursor.getColumnIndexOrThrow(Observation.C_ADDITIONAL_COMMENTS));

                //set data observation

                observationInputDetails.setText(observationName);
                observationTimeInputDetails.setText(observationTime);
                additionalCommentsInputDetails.setText(additionalComments);

            }while (cursor.moveToNext());
        }
    }
}