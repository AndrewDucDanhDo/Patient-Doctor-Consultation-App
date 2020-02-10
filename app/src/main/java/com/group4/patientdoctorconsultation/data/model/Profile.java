package com.group4.patientdoctorconsultation.data.model;

import com.group4.patientdoctorconsultation.common.IndexedFirestoreResource;

import java.util.List;
import java.util.Map;

public class Profile extends IndexedFirestoreResource {

    public static final String COLLECTION_NAME = "profiles";

    public static final String PROFILE_TYPE_PATIENT = "patient";
    public static final String PROFILE_TYPE_DOCTOR = "doctor";

    public static final String FIELD_PROFILE_TYPE = "profileType";
    public static final String FIELD_USER_NAME = "userName";
    public static final String FIELD_FIRST_NAME = "firstName";
    public static final String FIELD_LAST_NAME = "lastName";
    public static final String FIELD_GENDER = "gender";
    public static final String FIELD_AGE_IN_YEARS = "ageInYears";
    public static final String FIELD_HEIGHT_IN_CENTIMETRES = "heightInCentimetres";
    public static final String FIELD_WEIGHT_IN_KG = "weightInKg";
    public static final String FIELD_MEDICAL_CONDITIONS = "medicalConditions";
    public static final String FIELD_LINKED_PROFILES = "linkedProfiles";

    private String profileType;
    private String userName;
    private String firstName;
    private String lastName;
    private String gender;
    private int ageInYears;
    private String heightInCentimetres;
    private String weightInKg;
    private List<String> medicalConditions;
    private Map<String, Boolean> linkedProfiles;

    public String getProfileType() {
        return profileType;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAgeInYears() {
        return ageInYears;
    }

    public void setAgeInYears(int ageInYears) {
        this.ageInYears = ageInYears;
    }

    public String getHeightInCentimetres() {
        return heightInCentimetres;
    }

    public void setHeightInCentimetres(String heightInCentimetres) {
        this.heightInCentimetres = heightInCentimetres;
    }

    public String getWeightInKg() {
        return weightInKg;
    }

    public void setWeightInKg(String weightInKg) {
        this.weightInKg = weightInKg;
    }

    public List<String> getMedicalConditions() {
        return medicalConditions;
    }

    public void setMedicalConditions(List<String> medicalConditions) {
        this.medicalConditions = medicalConditions;
    }

    public Map<String, Boolean> getLinkedProfiles() {
        return linkedProfiles;
    }

    public void setLinkedProfiles(Map<String, Boolean> linkedProfiles) {
        this.linkedProfiles = linkedProfiles;
    }
}
