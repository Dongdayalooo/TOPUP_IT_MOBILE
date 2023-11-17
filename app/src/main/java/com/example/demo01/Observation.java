package com.example.demo01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

// Thêm các trường cần thiết vào lớp "Observation"
public class Observation extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "OBSERVATION_DB";
    // database version
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "observations";
    public static final String C_OBSERVATION_ID = "obsid";
    public static final String C_ID = "hike_id";
    public static final String C_OBSERVATION = "observation";
    public static final String C_OBSERVATION_TIME = "observation_time";
    public static final String C_ADDITIONAL_COMMENTS = "additional_comments";


    // query for create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + C_OBSERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_ID + " TEXT, "
            + C_OBSERVATION + " TEXT, "
            + C_OBSERVATION_TIME + " TEXT, "
            + C_ADDITIONAL_COMMENTS + " TEXT "
            + ")";

    public Observation(@Nullable Context context) {
        super(context, Observation.DATABASE_NAME, null, Observation.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table on database
        db.execSQL(Observation.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //upgrade tale if any structure change in db
        //drop table if exists
        db.execSQL("DROP TABLE IF EXISTS " + Observation.TABLE_NAME);

        //create table again
        onCreate(db);
    }


    //add observation
    public long insertObservation(String hikeId, String observation, String observationTime, String additionalComments) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_ID, hikeId);
        values.put(C_OBSERVATION, observation);
        values.put(C_OBSERVATION_TIME, observationTime);
        values.put(C_ADDITIONAL_COMMENTS, additionalComments);
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }
    //update observation
    public void updateObservation(String obsId, String hikeId, String observation, String observationTime, String additionalComments) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_OBSERVATION, observation);
        values.put(C_OBSERVATION_TIME, observationTime);
        values.put(C_ADDITIONAL_COMMENTS, additionalComments);
        db.update(Observation.TABLE_NAME, values, Observation.C_OBSERVATION_ID + "=?", new String[]{obsId});
        db.close();
    }
    //delete observation by id
    public void deleteObservation(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Observation.TABLE_NAME, Observation.C_OBSERVATION_ID + "=?", new String[]{id});
        db.close();
    }

    // Lấy tất cả quan sát dựa trên ID của hike
    public ArrayList<ModelObservation> getAllObservationsForHike(String hikeId) {
        ArrayList<ModelObservation> observationList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Observation.TABLE_NAME + " WHERE " + Observation.C_ID + " = " + hikeId;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ModelObservation modelObservation = new ModelObservation(
                        "" + cursor.getInt(cursor.getColumnIndexOrThrow(C_OBSERVATION_ID)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(C_ID)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(C_OBSERVATION)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(C_OBSERVATION_TIME)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(C_ADDITIONAL_COMMENTS))
                );
                observationList.add(modelObservation);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return observationList;
    }

}
