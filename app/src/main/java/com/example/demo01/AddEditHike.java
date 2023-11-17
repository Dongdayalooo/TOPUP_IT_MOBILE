package com.example.demo01;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class AddEditHike extends AppCompatActivity {
    private EditText hikeNameEt,hikeLocationEt,hikeDateEt,hikeLengthEt,hikeTimeEt,hikeDifficultyLevelEt, hikeStopPointEt, hikeDescriptionEt;
    private RadioGroup radioGroupParking;
    RadioButton radioBtnYes, radioBtnNo;
    private boolean isParkingSelected;
    private FloatingActionButton fab;
    private Button btn;

    //action bar
    ActionBar actionBar;

    // string variable
    private String id,hikeName, hikeLocation, hikeDate, hikeLength, hikeTime, hikeStopPoint, hikeDescription, hikeDifficulty;
    private boolean hikeGroupParking;
    private Boolean isEditMode;
    //database helper
    private DbHelper dbHelper;

    //observation
    private boolean hikeId;
    private Observation observationdb;
    private ObservationAdapter observationAdapter;
    private RecyclerView observationRv;

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
        //init db observation
        observationdb = new Observation(this);
        observationRv = findViewById(R.id.observationRv);
        loadData();
        //init view hike
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
        btn = findViewById(R.id.addObservationButton);
        //get inite data (edit hike)
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

        hikeTimeEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

    }
    //load data observation
    private void loadData() {
        observationAdapter = new ObservationAdapter(this, observationdb.getAllObservationsForHike(id));
        observationRv.setAdapter(observationAdapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadData(); //refresh data
    }

    // move to ObservationActivity
    public void openObservationActivity(View view) {
        Intent intent = new Intent(this, ObservationInput.class);
        intent.putExtra("hikeId", id); // Chuyển ID của hike vào Intent
        startActivity(intent);
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
            isParkingSelected = radioButton.getText().equals("Yes");
        } else {
            // Xử lý trường hợp không chọn nút radio
            // Handle the case of not selecting a radio button
            isParkingSelected = false;
        }
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
                long id = dbHelper.insertHike(
                        hikeName,
                        hikeLocation,
                        hikeDate,
                        hikeLength,
                        hikeTime,
                        hikeStopPoint,
                        hikeDifficulty,
                        isParkingSelected,
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
    //set double click show ra lịch đẻ chọn ngày.
    public void showDatePickerDialog(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view1, year, month, dayOfMonth) -> {
            // Xử lý ngày được chọn ở đây
            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            hikeDateEt.setText(selectedDate);
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    // show time để người dùng chọn
    public void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            // Xử lý thời gian được chọn ở đây
            hikeTimeEt.setText(String.format("%02d:%02d", hourOfDay, minute));
        }, 0, 0, true);
        timePickerDialog.show();
    }

}