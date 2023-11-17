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

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.ObservationViewHolder> {
    private ArrayList<ModelObservation> observationList;
    private Observation observationdb;
    private Context context;

    // Constructor
    public ObservationAdapter(Context context,ArrayList<ModelObservation> observationList) {
        this.observationList = observationList;
        this.context = context;
        observationdb = new Observation(context);
    }

    @NonNull
    @Override
    public ObservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.observation_list_item, parent, false);
        ObservationViewHolder vh = new ObservationViewHolder(view);
        return (vh);
    }

    @Override
    public void onBindViewHolder(@NonNull ObservationViewHolder holder, int position) {
        ModelObservation modelObservation = observationList.get(position);
        //get data
        String obsId = modelObservation.getObservationId();
        String observation = modelObservation.getObservation();
        String observationTime = modelObservation.getObservationTime();
        String addComments = modelObservation.getAdditionalComments();
        //set date in view
        holder.observationText.setText(observation);
        holder.observationTimeText.setText(observationTime);
//        holder.additionalCommentsText.setText(addComments);


        //handle item click and show Observation details
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo và khởi tạo ý định chuyển sang ObservationDetails với ID của Observation làm tham chiếu
                Intent intent = new Intent(context, ObservationDetails.class);
                intent.putExtra("obsId", obsId);
                context.startActivity(intent);
            }
        });

        //handle click listener for Edit button
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for the Edit button
                Toast.makeText(context, "Edit button clicked for " + observation, Toast.LENGTH_SHORT).show();

                //create intent di chuyển ObservationInput để cập nhật data
                Intent intent1 = new Intent(context, ObservationInput.class);
                intent1.putExtra("OBSID",obsId);
                intent1.putExtra("OBSERVATION",observation);
                intent1.putExtra("OBSERVATIONTIME",observationTime);
                intent1.putExtra("ADDITIONALCOMMENTS",addComments);

                intent1.putExtra("isEditMode", true);
                //start intent
                context.startActivity(intent1);
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
                        observationdb.deleteObservation(obsId);
                        // Refresh data by notifying the activity or fragment
                        ((AddEditHike) context).onResume();
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
        return observationList.size();
    }

    // ViewHolder class
    class ObservationViewHolder extends RecyclerView.ViewHolder {
        //view for observation_list_item
        TextView observationText, observationTimeText;
        Button btnEdit,btnDelete;

        public ObservationViewHolder(@NonNull View itemView) {
            super(itemView);
            observationText = itemView.findViewById(R.id.observationTitle);
            observationTimeText = itemView.findViewById(R.id.observationTime);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
