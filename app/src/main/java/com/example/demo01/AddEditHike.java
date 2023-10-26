package com.example.demo01;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddEditHike extends AppCompatActivity {
    private EditText hikeNameEt,hikeLocationEt,hikeDateEt,hikeLengthEt,hikeTimeEt,hikeDifficultyLevelEt, hikeStopPointEt, hikeDescriptionEt;
    private RadioGroup radioGroupParking;
    RadioButton radioBtnYes, radioBtnNo;
    private boolean isParkingSelected;
    private FloatingActionButton fab;

    //action bar
    ActionBar actionBar;

    // string variable
    private String id,hikeName, hikeLocation, hikeDate, hikeLength, hikeTime, hikeStopPoint, hikeDescription, hikeDifficulty;
    private boolean hikeGroupParking;
    private Boolean isEditMode;
    //database helper
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_hike);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add New Hike");
        actionBar = getSupportActionBar();
        //button back
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //init db
        dbHelper = new DbHelper(this);

        //init view
        hikeNameEt = findViewById(R.id.hikeNameEt);
        hikeLocationEt = findViewById(R.id.hikeLocationEt);
        hikeDateEt = findViewById(R.id.hikeDateEt);
        hikeLengthEt = findViewById(R.id.hikeLengthEt);
        hikeTimeEt = findViewById(R.id.hikeTimeEt);
        hikeDifficultyLevelEt = findViewById(R.id.hikeDifficultyLevelEt);
        hikeStopPointEt = findViewById(R.id.hikeStopPointEt);
        hikeDescriptionEt = findViewById(R.id.hikeDescriptionEt);
        radioGroupParking = findViewById(R.id.radioGroupParking);
        radioBtnYes = findViewById(R.id.radioBtnYes);
        radioBtnNo = findViewById(R.id.radioBtnNo);
        fab = findViewById(R.id.fab);

        //get inite data
        Intent intent = getIntent();
        isEditMode = intent.getBooleanExtra("isEditMode", false);

        if(isEditMode){
            //set toolbar title
            actionBar.setTitle("Update Hike");

            //get the other value from intent
            id = intent.getStringExtra("ID");
            hikeName = intent.getStringExtra("HIKENAME");
            hikeLocation = intent.getStringExtra("HIKELOCATION");
            hikeDate = intent.getStringExtra("HIKEDATE");
            hikeLength = intent.getStringExtra("HIKELENGTH");
            hikeTime = intent.getStringExtra("HIKETIME");
            hikeDifficulty = intent.getStringExtra("HIKEDIFFICULTYLEVEL");
            hikeStopPoint = intent.getStringExtra("HIKESTOPPOINT");
            hikeGroupParking = intent.getBooleanExtra("HIKEGROUPPARKING", false); // Assuming false as default value
            hikeDescription = intent.getStringExtra("HIKEDESCRIPTION");

            //đặt giá trị trong trường btnEdit
            hikeNameEt.setText(hikeName);
            hikeLocationEt.setText(hikeLocation);
            hikeDateEt.setText(hikeDate);
            hikeLengthEt.setText(hikeLength);
            hikeTimeEt.setText(hikeTime);
            hikeDifficultyLevelEt.setText(hikeDifficulty);
            hikeStopPointEt.setText(hikeStopPoint);
            if (hikeGroupParking) {
                radioBtnYes.setChecked(true);
            } else {
                radioBtnNo.setChecked(true);
            }
            hikeDescriptionEt.setText(hikeDescription);
        }else{
            //add mode on
            actionBar.setTitle("Add New Hike");
        }

        //add even handler
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

    }

    private void saveData() {

        //task user given data in variable
        hikeName = hikeNameEt.getText().toString();
        hikeLocation = hikeLocationEt.getText().toString();
        hikeDate = hikeDateEt.getText().toString();
        hikeLength = hikeLengthEt.getText().toString();
        hikeTime = hikeTimeEt.getText().toString();
        hikeStopPoint = hikeStopPointEt.getText().toString();
        hikeDescription = hikeDescriptionEt.getText().toString();
        hikeDifficulty = hikeDifficultyLevelEt.getText().toString();
        int selectedId = radioGroupParking.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton radioButton = radioGroupParking.findViewById(selectedId);
            isParkingSelected = radioButton.getText().equals("Có");
        } else {
            // Handle case when no radio button is selected
            isParkingSelected = false; // or any other default value
        }

//        //check file data
//        if (!hikeName.isEmpty() && !hikeLocation.isEmpty() && !hikeDate.isEmpty() && !hikeLength.isEmpty() && !hikeTime.isEmpty() && !hikeStopPoint.isEmpty() && !hikeDescription.isEmpty() && !hikeDifficulty.isEmpty())
//        {
//            //save data, it user have only on data
//            //function for save data on SQLite database
//
//            long id = dbHelper.insertHike(
//                    hikeName,
//                    hikeLocation,
//                    hikeDate,
//                    hikeLength,
//                    hikeTime,
//                    hikeStopPoint,
//                    hikeDifficulty,
//                    isParkingSelected, // Change this line to pass the value of isParkingSelected
//                    hikeDescription
//            );
//
//            //To check insert data successfully, show a toast message
//            Toast.makeText(getApplicationContext(), "Inserted Successfully..."+ id, Toast.LENGTH_SHORT).show();
//
//        }else{
//            Toast.makeText(getApplicationContext(),"Nothing to save...",Toast.LENGTH_SHORT).show();
//        }
//
//        //check edit or add mode to save data in sql
//        if(isEditMode){
//            //edit
//             dbHelper.updateHike(
//                     id,
//                    hikeName,
//                    hikeLocation,
//                    hikeDate,
//                    hikeLength,
//                    hikeTime,
//                    hikeStopPoint,
//                    hikeDifficulty,
//                    isParkingSelected, // Change this line to pass the value of isParkingSelected
//                    hikeDescription
//            );
//            //To check insert data successfully, show a toast message
//            Toast.makeText(getApplicationContext(), "Update Successfully..."+ id, Toast.LENGTH_SHORT).show();
//        }


        //test
        //check file data
        if (!hikeName.isEmpty() && !hikeLocation.isEmpty() && !hikeDate.isEmpty() && !hikeLength.isEmpty() && !hikeTime.isEmpty() && !hikeStopPoint.isEmpty() && !hikeDescription.isEmpty() && !hikeDifficulty.isEmpty())
        {
            //check edit or add mode to save data in sql
            if(isEditMode){
                //edit mode
                dbHelper.updateHike(
                        ""+ id,
                        ""+ hikeName,
                        "" + hikeLocation,
                        "" + hikeDate,
                        "" + hikeLength,
                        ""+ hikeTime,
                        ""+ hikeStopPoint,
                        "" + hikeDifficulty,
                        isParkingSelected,
                        "" + hikeDescription
                );
                //To check insert data successfully, show a toast message
                Toast.makeText(getApplicationContext(), "Update Successfully..."+ id, Toast.LENGTH_SHORT).show();
            }else{
                //add mode
                //save data, it user have only on data
                //function for save data on SQLite database
                long id = dbHelper.insertHike(
                        hikeName,
                        hikeLocation,
                        hikeDate,
                        hikeLength,
                        hikeTime,
                        hikeStopPoint,
                        hikeDifficulty,
                        isParkingSelected, // Change this line to pass the value of isParkingSelected
                        hikeDescription
                );

                //To check insert data successfully, show a toast message
                Toast.makeText(getApplicationContext(), "Inserted Successfully..."+ id, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Nothing to save...",Toast.LENGTH_SHORT).show();
        }


    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}