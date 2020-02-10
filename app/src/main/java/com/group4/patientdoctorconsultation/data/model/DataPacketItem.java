package com.group4.patientdoctorconsultation.data.model;

import com.group4.patientdoctorconsultation.R;

import java.io.Serializable;

public class DataPacketItem implements Serializable {

    public enum DataPacketItemType{
        HEART_RATE,
        DOCUMENT_REFERENCE,
        LOCATION,
        COMMENT,
        NOTE
    }

    private DataPacketItemType dataPacketItemType;
    private int iconResourceId;
    private String value;
    private String displayValue;

    public DataPacketItem(DataPacketItemType dataPacketItemType, String value, String displayValue){
        this.displayValue = displayValue;
        this.dataPacketItemType = dataPacketItemType;
        this.value = value;
        setIconResourceId();
    }

    public DataPacketItem(DataPacketItemType dataPacketItemType, String value) {
        this.dataPacketItemType = dataPacketItemType;
        this.value = value;
        this.displayValue = dataPacketItemType == DataPacketItemType.DOCUMENT_REFERENCE ? null : value;
        setIconResourceId();
    }

    public DataPacketItemType getDataPacketItemType() {
        return dataPacketItemType;
    }

    public void setDataPacketItemType(DataPacketItemType dataPacketItemType) {
        this.dataPacketItemType = dataPacketItemType;
        setIconResourceId();
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    private void setIconResourceId() {
        switch(dataPacketItemType){
            case HEART_RATE:
                iconResourceId = R.drawable.ic_favorite_black_24dp;
                break;
            case DOCUMENT_REFERENCE:
                iconResourceId = R.drawable.ic_attach_file_black_24dp;
                break;
            case LOCATION:
                iconResourceId = R.drawable.ic_location_on_black_24dp;
                break;
            case COMMENT:
                iconResourceId = R.drawable.ic_text_fields_black_24dp;
                break;
            case NOTE:
                iconResourceId = R.drawable.ic_text_fields_black_24dp;
                break;
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getImageUrl(){
        return dataPacketItemType == DataPacketItemType.DOCUMENT_REFERENCE ? value : null;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }
}
