package com.group4.patientdoctorconsultation.ui.dialogfragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.group4.patientdoctorconsultation.R;
import com.group4.patientdoctorconsultation.common.PacketItemDialog;
import com.group4.patientdoctorconsultation.data.model.DataPacketItem;

public class TextDialogFragment extends PacketItemDialog {

    EditText inputText;

    @Override
    public String getDialogResult(){
        if(inputText != null){
            return inputText.getText().toString();
        }else{
            return "";
        }
    }

    @Override
    public View getView(LayoutInflater inflater) {

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_dialog_text, null);
        inputText = view.findViewById(R.id.text_input);

        return view;
    }

    @Override
    public DataPacketItem.DataPacketItemType getPacketItemType() {
        return DataPacketItem.DataPacketItemType.NOTE;
    }

}
