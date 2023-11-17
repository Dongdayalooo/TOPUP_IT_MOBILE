package com.example.demo01;

public class ModelObservation {
    private String observationId;
    private String hikeId;
    private String observation;
    private String observationTime;
    private String additionalComments;

    //create constructer
    public ModelObservation(String observationId, String hikeId, String observation, String observationTime, String additionalComments) {
        this.observationId = observationId;
        this.hikeId = hikeId;
        this.observation = observation;
        this.observationTime = observationTime;
        this.additionalComments = additionalComments;
    }

    //create getter,setter method
    public String getObservationId() {
        return observationId;
    }

    public void setObservationId(String observationId) {
        this.observationId = observationId;
    }

    public String getHikeId() {
        return hikeId;
    }

    public void setHikeId(String hikeId) {
        this.hikeId = hikeId;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getObservationTime() {
        return observationTime;
    }

    public void setObservationTime(String observationTime) {
        this.observationTime = observationTime;
    }

    public String getAdditionalComments() {
        return additionalComments;
    }

    public void setAdditionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
    }
}

