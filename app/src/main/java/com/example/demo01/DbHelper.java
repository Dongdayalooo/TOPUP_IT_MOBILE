package com.example.demo01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

// class for database helper
public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, Hike.DATABASE_NAME, null, Hike.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table on database
        db.execSQL(Hike.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //upgrade tale if any structure change in db
        //drop table if exists
        db.execSQL("DROP TABLE IF EXISTS " + Hike.TABLE_NAME);
        //create table again
        onCreate(db);
    }


    //Insert Function to insert data in database
    public long insertHike(String hikeName, String hikeLocation, String hikeDate, String hikeLength, String hikeTime,
                           String hikeStopPoint, String hikeDifficultyLevel, boolean hikeGroupParking, String hikeDescription) {
        //get writable database to write data on db
        SQLiteDatabase db = this.getWritableDatabase();
        // tạo đối tượng lớp ContentValues để lưu data
        ContentValues contentVl = new ContentValues();
        // id will save automatically as we write query
        contentVl.put(Hike.C_HikeName, hikeName);
        contentVl.put(Hike.C_HikeLocation, hikeLocation);
        contentVl.put(Hike.C_HikeDate, hikeDate);
        contentVl.put(Hike.C_HikeLength, hikeLength);
        contentVl.put(Hike.C_HikeTime, hikeTime);
        contentVl.put(Hike.C_HikeStopPoint, hikeStopPoint);
        contentVl.put(Hike.C_HikeDifficultyLevel, hikeDifficultyLevel);
        contentVl.put(Hike.C_GroupParking, hikeGroupParking ? 1 : 0); // Convert boolean to integer
        contentVl.put(Hike.C_HikeDescription, hikeDescription);
        //insert data in row, nó sẽ trả về id của bản ghi
        long id = db.insert(Hike.TABLE_NAME, null, contentVl);
        db.close();
        return id;
    }

    //update Funccton to update data in database
    public void updateHike(String id, String hikeName, String hikeLocation, String hikeDate, String hikeLength,
                           String hikeTime, String hikeStopPoint, String hikeDifficultyLevel, boolean hikeGroupParking, String hikeDescription) {
        //get writable database to write data on db
        SQLiteDatabase db = this.getWritableDatabase();
        // create ContentValues class object to save data
        ContentValues contentVl = new ContentValues();
        contentVl.put(Hike.C_HikeName, hikeName);
        contentVl.put(Hike.C_HikeLocation, hikeLocation);
        contentVl.put(Hike.C_HikeDate, hikeDate);
        contentVl.put(Hike.C_HikeLength, hikeLength);
        contentVl.put(Hike.C_HikeTime, hikeTime);
        contentVl.put(Hike.C_HikeStopPoint, hikeStopPoint);
        contentVl.put(Hike.C_HikeDifficultyLevel, hikeDifficultyLevel);
        contentVl.put(Hike.C_GroupParking, hikeGroupParking ? 1 : 0); // Convert boolean to integer
        contentVl.put(Hike.C_HikeDescription, hikeDescription);
        //insert data in row, nó sẽ trả về id của bản ghi
        db.update(Hike.TABLE_NAME, contentVl, Hike.C_ID + "=?",new String[]{id} );
        db.close();
    }

    //delete data by id
    public void deleteHike(String id) {
        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Hike.TABLE_NAME, Hike.C_ID + " = ?", new String[]{id});
        db.close();
    }

    //delete all data
//    public void deleteAllHike() {
//        //get writable database
//        SQLiteDatabase db = getWritableDatabase();
//
//        //query for delete
//        db.execSQL("DELETE FROM " + Hike.TABLE_NAME);
//        db.close();
//    }

    //get data hike
    public ArrayList<ModelHike> getAllData() {
        //create arrayList
        ArrayList<ModelHike> arrayList = new ArrayList<>();
        //sql command query
        String selectQuery = "SELECT * FROM " + Hike.TABLE_NAME;
        //get readable db
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ModelHike modelHike = new ModelHike(
                        "" + cursor.getInt(cursor.getColumnIndexOrThrow(Hike.C_ID)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeName)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeLocation)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeDate)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeLength)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeTime)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeStopPoint)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeDifficultyLevel)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(Hike.C_GroupParking)) == 1,
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeDescription))
                );
                arrayList.add(modelHike);
            } while (cursor.moveToNext());
        }

        db.close();
        return arrayList;
    }

    //search data in sql database
    public ArrayList<ModelHike> getSearchHike(String query) {
        ArrayList<ModelHike> hikeList = new ArrayList<>();
        //get readable database
        SQLiteDatabase db = getReadableDatabase();
        //query for search
        String queryToSearch = "SELECT * FROM " + Hike.TABLE_NAME + " WHERE " + Hike.C_HikeName + " LIKE '%" + query + "%'";
        Cursor cursor = db.rawQuery(queryToSearch, null);
        if (cursor.moveToFirst()) {
            do {
                ModelHike modelHike = new ModelHike(
                        "" + cursor.getInt(cursor.getColumnIndexOrThrow(Hike.C_ID)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeName)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeLocation)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeDate)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeLength)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeTime)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeStopPoint)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeDifficultyLevel)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(Hike.C_GroupParking)) == 1,
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Hike.C_HikeDescription))
                );
                hikeList.add(modelHike);
            } while (cursor.moveToNext());
        }

        db.close();
        return hikeList;
    }
}
