package com.example.demo01;

public class Hike {
    // database or db name
    public static final String DATABASE_NAME = "HIKE_DB";
    // database version
    public static final int DATABASE_VERSION = 1;

    // table name
    public static final String TABLE_NAME = "HIKE_TABLE";

    // table column or field name
    public static final String C_ID = "ID";
    public static final String C_HikeName = "HikeName";
    public static final String C_HikeLocation = "HikeLocation";
    public static final String C_HikeDate = "HikeDate";
    public static final String C_HikeLength = "HikeLength";
    public static final String C_HikeTime = "HikeTime";
    public static final String C_HikeStopPoint = "HikeStopPoint";
    public static final String C_HikeDifficultyLevel = "HikeDifficultyLevel";
    public static final String C_GroupParking = "GroupParking";
    public static final String C_HikeDescription = "HikeDescription";

    // query for create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_HikeName + " TEXT, "
            + C_HikeLocation + " TEXT, "
            + C_HikeDate + " TEXT, "
            + C_HikeLength + " TEXT, "
            + C_HikeTime + " TEXT, "
            + C_HikeStopPoint + " TEXT, "
            + C_HikeDifficultyLevel + " TEXT, "
            + C_GroupParking + " INTEGER, "
            + C_HikeDescription + " TEXT"
            + ")";
}

