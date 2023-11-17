package com.example.demo01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    //view
    private FloatingActionButton fab;

    //adapter
    private AdapterHike adapterHike;
    //action bar
    private ActionBar actionBar;
    private RecyclerView hikeRv;
    //db
    private DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Add the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        //init db
        dbHelper = new DbHelper(this);
        //initialization
        fab = findViewById(R.id.fab);
        hikeRv = findViewById(R.id.hikeRv);
        hikeRv.setHasFixedSize(true);
        //add btn
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // move to new activity to add
                Intent intent = new Intent(MainActivity.this, AddEditHike.class);
                startActivity(intent);
            }
        });

        loadData();
    }

    private void loadData() {
        adapterHike = new AdapterHike(this,dbHelper.getAllData());
        hikeRv.setAdapter(adapterHike);
    }

    //ctrl + o
    @Override
    protected void onResume() {
        super.onResume();
        loadData(); //refresh data
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_top_menu, menu);
        //get search item for menu
        MenuItem item = menu.findItem(R.id.searchHike);
        //khu vực search
        SearchView searchView = (SearchView) item.getActionView();
        //set max value for with
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchHike(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchHike(newText);
                return false;
            }
        });
        return true;
    }
    private void searchHike(String query) {
        adapterHike = new AdapterHike(this,dbHelper.getSearchHike(query));
        hikeRv.setAdapter(adapterHike);
    }

// đang lỗi
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.deleteAllHike:
//            dbHelper.deleteAllHike();
//            onResume();
//            break;
//        }
//        return true;
//    }
}