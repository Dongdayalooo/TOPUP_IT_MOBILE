package com.example.demo01;

public class ModelHike {

    private String id;
    private String hikeName;
    private String hikeLocation;
    private String hikeDate;
    private String hikeLength;
    private String hikeTime;
    private String hikeStopPoint;
    private String hikeDifficultyLevel;
    private Boolean  groupParking;
    private String hikeDescription;


    //create constructer
    public ModelHike(String id, String hikeName, String hikeLocation, String hikeDate, String hikeLength, String hikeTime, String hikeStopPoint, String hikeDifficultyLevel, Boolean groupParking, String hikeDescription) {
        this.id = id;
        this.hikeName = hikeName;
        this.hikeLocation = hikeLocation;
        this.hikeDate = hikeDate;
        this.hikeLength = hikeLength;
        this.hikeTime = hikeTime;
        this.hikeStopPoint = hikeStopPoint;
        this.hikeDifficultyLevel = hikeDifficultyLevel;
        this.groupParking = groupParking;
        this.hikeDescription = hikeDescription;
    }

    //create getter,setter method
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHikeName() {
        return hikeName;
    }

    public void setHikeName(String hikeName) {
        this.hikeName = hikeName;
    }

    public String getHikeLocation() {
        return hikeLocation;
    }

    public void setHikeLocation(String hikeLocation) {
        this.hikeLocation = hikeLocation;
    }

    public String getHikeDate() {
        return hikeDate;
    }

    public void setHikeDate(String hikeDate) {
        this.hikeDate = hikeDate;
    }

    public String getHikeLength() {
        return hikeLength;
    }

    public void setHikeLength(String hikeLength) {
        this.hikeLength = hikeLength;
    }

    public String getHikeTime() {
        return hikeTime;
    }

    public void setHikeTime(String hikeTime) {
        this.hikeTime = hikeTime;
    }

    public String getHikeStopPoint() {
        return hikeStopPoint;
    }

    public void setHikeStopPoint(String hikeStopPoint) {
        this.hikeStopPoint = hikeStopPoint;
    }

    public String getHikeDifficultyLevel() {
        return hikeDifficultyLevel;
    }

    public void setHikeDifficultyLevel(String hikeDifficultyLevel) {
        this.hikeDifficultyLevel = hikeDifficultyLevel;
    }

    public Boolean isGroupParking() {
        return groupParking;
    }

    public void setGroupParking(Boolean groupParking) {
        this.groupParking = groupParking;
    }

    public String getHikeDescription() {
        return hikeDescription;
    }

    public void setHikeDescription(String hikeDescription) {
        this.hikeDescription = hikeDescription;
    }


}
