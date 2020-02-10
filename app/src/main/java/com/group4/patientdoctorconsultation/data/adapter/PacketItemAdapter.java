package com.group4.patientdoctorconsultation.data.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.group4.patientdoctorconsultation.R;
import com.group4.patientdoctorconsultation.common.BindingAdapter;
import com.group4.patientdoctorconsultation.common.ClickListener;
import com.group4.patientdoctorconsultation.data.model.DataPacketItem;
import com.group4.patientdoctorconsultation.databinding.ItemDataPacketItemBinding;

public class PacketItemAdapter extends BindingAdapter<DataPacketItem, ItemDataPacketItemBinding> {

    public PacketItemAdapter(ClickListener<DataPacketItem> clickListener){
        super(clickListener);
    }

    @Override
    protected ItemDataPacketItemBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        return DataBindingUtil.inflate(inflater, R.layout.item_data_packet_item, parent, false);
    }

    @Override
    protected void bind(ItemDataPacketItemBinding binding, DataPacketItem dataPacketItem) {
        binding.setDataPacketItem(dataPacketItem);
    }

}
