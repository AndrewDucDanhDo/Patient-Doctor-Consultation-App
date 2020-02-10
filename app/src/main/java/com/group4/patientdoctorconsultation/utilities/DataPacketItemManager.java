package com.group4.patientdoctorconsultation.utilities;

import com.group4.patientdoctorconsultation.data.model.DataPacket;
import com.group4.patientdoctorconsultation.data.model.DataPacketItem;

import java.util.ArrayList;
import java.util.List;

public class DataPacketItemManager {

    public static DataPacket buildDataPacket(DataPacket dataPacket, List<DataPacketItem> packetItems) {
        if (packetItems == null || packetItems.isEmpty()) {
            return null;
        }

        List<String> documentReferences = new ArrayList<>();
        List<String> comments = new ArrayList<>();
        List<String> notes = new ArrayList<>();
        List<String> locations = new ArrayList<>();

        for (DataPacketItem dataPacketItem : packetItems) {
            switch (dataPacketItem.getDataPacketItemType()) {
                case HEART_RATE:
                    dataPacket.setHeartRate(dataPacketItem.getValue());
                    break;
                case DOCUMENT_REFERENCE:
                    documentReferences.add(dataPacketItem.getValue());
                    break;
                case LOCATION:
                    locations.add(dataPacketItem.getValue());
                    break;
                case COMMENT:
                    comments.add(dataPacketItem.getValue());
                    break;
                case NOTE:
                    notes.add(dataPacketItem.getValue());
                    break;
            }
        }

        dataPacket.setDocumentReferences(documentReferences);
        dataPacket.setComments(comments);
        dataPacket.setNotes(notes);
        dataPacket.setLocations(locations);

        return dataPacket;
    }

    public static List<DataPacketItem> breakDownDataPacket(DataPacket newPacket) {
        List<DataPacketItem> dataPacketItems = new ArrayList<>();

        if (newPacket.getHeartRate() != null) {
            dataPacketItems.add(new DataPacketItem(DataPacketItem.DataPacketItemType.HEART_RATE, newPacket.getHeartRate()));
        }

        if (newPacket.getDocumentReferences() != null) {
            for (String documentReference : newPacket.getDocumentReferences()) {
                dataPacketItems.add(new DataPacketItem(DataPacketItem.DataPacketItemType.DOCUMENT_REFERENCE, documentReference));
            }
        }

        if (newPacket.getComments() != null) {
            for (String comment : newPacket.getComments()) {
                dataPacketItems.add(new DataPacketItem(DataPacketItem.DataPacketItemType.COMMENT, comment));
            }
        }

        if (newPacket.getNotes() != null) {
            for (String note : newPacket.getNotes()) {
                dataPacketItems.add(new DataPacketItem(DataPacketItem.DataPacketItemType.NOTE, note));
            }
        }

        if (newPacket.getLocations() != null) {
            for (String location : newPacket.getLocations()) {
                dataPacketItems.add(new DataPacketItem(DataPacketItem.DataPacketItemType.LOCATION, location));
            }
        }

        return dataPacketItems;
    }

}
