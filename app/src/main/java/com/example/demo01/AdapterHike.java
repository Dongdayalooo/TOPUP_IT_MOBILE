package com.example.demo01;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterHike extends RecyclerView.Adapter<AdapterHike.HikeViewHolder>{

    private Context context;
    private ArrayList<ModelHike> hikeList;
    private DbHelper dbHelper;

    //add constructor
    public AdapterHike(Context context, ArrayList<ModelHike> hikeList) {
        this.context = context;
        this.hikeList = hikeList;
        dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public HikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_hike_item,parent,false);
        HikeViewHolder vh = new HikeViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HikeViewHolder holder, int position) {
        ModelHike modelHike = hikeList.get(position);

        //get data
        String id = modelHike.getId();
        String hikeName = modelHike.getHikeName();
        String hikeLocation = modelHike.getHikeLocation();
        String hikeDate = modelHike.getHikeDate();
        String hikeLength = modelHike.getHikeLength();
        String hikeTime = modelHike.getHikeTime();
        String hikeDifficultyLevel = modelHike.getHikeDifficultyLevel();
        String hikeStopPoint = modelHike.getHikeStopPoint();
        boolean hikeGroupParking = modelHike.isGroupParking();
        String hikeDescription = modelHike.getHikeDescription();

        //set date in view
        holder.hikeName.setText(hikeName);

        //handle click listener
        holder.hikeName.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        });
        //handle item click and show hike details
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create and initialize intent to move to HikeDetails activity with hike ID as reference
                Intent intent = new Intent(context, HikeDetails.class);
                intent.putExtra("Id", id); // Make sure you are passing the correct key
                context.startActivity(intent);
            }
        });

        //handle click listener for Edit button
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for the Edit button
                Toast.makeText(context, "Edit button clicked for " + hikeName, Toast.LENGTH_SHORT).show();
                //create intent to move AddEditActivity to update data
                Intent intent = new Intent(context, AddEditHike.class);
                intent.putExtra("ID",id);
                intent.putExtra("HIKENAME",hikeName);
                intent.putExtra("HIKELOCATION",hikeLocation);
                intent.putExtra("HIKEDATE",hikeDate);
                intent.putExtra("HIKELENGTH",hikeLength);
                intent.putExtra("HIKETIME",hikeTime);
                intent.putExtra("HIKEDIFFICULTYLEVEL",hikeDifficultyLevel);
                intent.putExtra("HIKESTOPPOINT",hikeStopPoint);
                intent.putExtra("HIKEGROUPPARKING",hikeGroupParking);
                intent.putExtra("HIKEDESCRIPTION",hikeDescription);

                //pass a boolean data to define it is for edit purpose
                intent.putExtra("isEditMode", true);
                //start intent
                context.startActivity(intent);

            }
        });

        //handle click listener for Delete button
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show an alert dialog for confirmation
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to delete this item?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Delete the item from the database
                        dbHelper.deleteHike(id);
                        // Refresh data by notifying the activity or fragment
                        ((MainActivity) context).onResume();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog if "No" is clicked
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return hikeList.size();
    }

    class HikeViewHolder extends RecyclerView.ViewHolder{

        //view for row_hike_item
        TextView hikeName;
        Button btnEdit, btnDelete;

        public HikeViewHolder(@NonNull View itemView) {
            super(itemView);

            //init view
            hikeName = itemView.findViewById(R.id.tv_hikeName);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
