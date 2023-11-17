package com.example.demo01;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class ObservationInput extends AppCompatActivity {
    private EditText observationInput, observationTimeInput, additionalCommentsInput;

    private Button observationSaveBtn, btnBack;
    private String obsId,hikeId,observation,observationTime,addComments;

    private Boolean isEditMode;

    private Observation observationdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_input);
        //init db
        observationdb = new Observation(this);
        // Lấy ID của Hike từ Intent
        Intent intent = getIntent();
        hikeId = intent.getStringExtra("hikeId");
        // Initialize views
        observationInput = findViewById(R.id.observationInput);
        observationTimeInput = findViewById(R.id.observationTimeInput);
        additionalCommentsInput = findViewById(R.id.additionalCommentsInput);
        //get inite data
        Intent intent1 = getIntent();
        isEditMode = intent1.getBooleanExtra("isEditMode", false);

        if (isEditMode){
            //get the other value from intent
            obsId = intent1.getStringExtra("OBSID");
            hikeId = intent1.getStringExtra("hikeId");
            observation = intent1.getStringExtra("OBSERVATION");
            observationTime = intent1.getStringExtra("OBSERVATIONTIME");
            addComments = intent1.getStringExtra("ADDITIONALCOMMENTS");

            //đặt giá trị trong trường btnEdit
            observationInput.setText(observation);
            observationTimeInput.setText(observationTime);
            additionalCommentsInput.setText(addComments);
        }

        // Xử lý sự kiện khi người dùng nhấn nút "Lưu"
        observationSaveBtn = findViewById(R.id.observationSaveBtn);
        observationSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveObservation();
            }
        });

        //xử lý khi người dùng click vao btnBack
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void saveObservation() {

        observation = observationInput.getText().toString();
        observationTime = observationTimeInput.getText().toString();
        addComments = additionalCommentsInput.getText().toString();

        if(isEditMode){
            observationdb.updateObservation(
                    ""+obsId,
                    ""+hikeId,
                    ""+observation,
                    ""+observationTime,
                    ""+addComments
            );
            Toast.makeText(getApplicationContext(), "Update Successfully..."+ obsId, Toast.LENGTH_SHORT).show();
        }else {
            long id = observationdb.insertObservation(
                    ""+ hikeId,
                    ""+observation,
                    ""+observationTime,
                    ""+addComments
            );
            // Hiển thị thông báo thành công
            Toast.makeText(getApplicationContext(), "Inserted Observation with ID: " + id, Toast.LENGTH_SHORT).show();
        }
    }

    //show time de cho người dùng chọn
    public void showTimePickerDialog(View v) {
        TimePickerDialog timePickerDialog;
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(ObservationInput.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                observationTimeInput.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
            }
        }, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
}