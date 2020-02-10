package com.group4.patientdoctorconsultation.data.model;

import com.group4.patientdoctorconsultation.common.IndexedFirestoreResource;

import java.util.List;

public class DataPacket extends IndexedFirestoreResource {

    public static final String COLLECTION_NAME = "data_packets";
    public static final String FIELD_DOCTOR_ID = "doctorId";
    public static final String FIELD_PATIENT_ID = "patientId";
    public static final String FIELD_DOCUMENT_REFERENCES = "documentReferences";
    public static final String FIELD_NOTES = "notes";
    public static final String FIELD_COMMENTS = "comments";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_HEART_RATE = "heartRate";
    public static final String FIELD_LOCATIONS = "locations";


    private String doctorId;
    private String doctorName;
    private String patientId;
    private String patientName;
    private String title;
    private List<String> documentReferences;
    private List<String> notes;
    private List<String> comments;
    private List<String> locations;
    private String heartRate;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName != null ? doctorName : "No doctor";
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getDocumentReferences() {
        return documentReferences;
    }

    public void setDocumentReferences(List<String> documentReferences) {
        this.documentReferences = documentReferences;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }
}