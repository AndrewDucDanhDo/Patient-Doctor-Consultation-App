package com.group4.patientdoctorconsultation.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.group4.patientdoctorconsultation.R;
import com.group4.patientdoctorconsultation.common.FirestoreFragment;
import com.group4.patientdoctorconsultation.common.PacketItemDialog;
import com.group4.patientdoctorconsultation.common.SwipeDeleteAction;
import com.group4.patientdoctorconsultation.data.adapter.PacketItemAdapter;
import com.group4.patientdoctorconsultation.data.model.DataPacket;
import com.group4.patientdoctorconsultation.data.model.DataPacketItem;
import com.group4.patientdoctorconsultation.databinding.FragmentDataPacketBinding;
import com.group4.patientdoctorconsultation.ui.dialogfragment.AttachmentDialogFragment;
import com.group4.patientdoctorconsultation.ui.dialogfragment.HeartRateDialogFragment;
import com.group4.patientdoctorconsultation.ui.dialogfragment.LocationDialogFragment;
import com.group4.patientdoctorconsultation.ui.dialogfragment.ProfileDialogFragment;
import com.group4.patientdoctorconsultation.ui.dialogfragment.TextDialogFragment;
import com.group4.patientdoctorconsultation.utilities.DataPacketItemManager;
import com.group4.patientdoctorconsultation.utilities.DependencyInjector;
import com.group4.patientdoctorconsultation.viewmodel.DataPacketViewModel;

import java.util.List;
import java.util.Objects;

public class DataPacketFragment extends FirestoreFragment implements View.OnClickListener {

    private static final int RC_PACKET_ITEM = 1;
    private static final int RC_PACKET_DOCTOR = 2;
    private static final String TAG = DataPacketFragment.class.getSimpleName();

    private PacketItemAdapter packetItemAdapter;
    private DataPacketViewModel viewModel;
    FragmentDataPacketBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_data_packet, container, false);
        viewModel = DependencyInjector.provideDataPacketViewModel(requireActivity());

        initialisePacketItemList(binding.packetItemList);

        viewModel.getActivePacket().observe(this, activePacket -> {
            if (activePacket != null && handleFirestoreResult(activePacket)) {
                updatePacketBinding(activePacket.getResource());
                binding.setDataPacket(activePacket.getResource());
            }
        });

        binding.newAttachment.setOnClickListener(this);
        binding.newComment.setOnClickListener(this);
        binding.newHeartRate.setOnClickListener(this);
        binding.newLocation.setOnClickListener(this);
        binding.doctorIcon.setOnClickListener(this);

        return binding.getRoot();
    }

    @SuppressLint("CommitTransaction")
    @Override
    public void onClick(View view) {
        PacketItemDialog itemDialog;
        int requestCode = RC_PACKET_ITEM;

        switch (view.getId()){
            case R.id.new_attachment:
                itemDialog = new AttachmentDialogFragment();
                break;
            case R.id.new_comment:
                itemDialog = new TextDialogFragment();
                break;
            case R.id.new_heart_rate:
                itemDialog = new HeartRateDialogFragment();
                break;
            case R.id.new_location:
                itemDialog = new LocationDialogFragment();
                break;
            case R.id.doctor_icon:
                itemDialog = new ProfileDialogFragment();
                requestCode = RC_PACKET_DOCTOR;
                break;
            default:
                itemDialog = new TextDialogFragment();
                break;
        }

        itemDialog.setTargetFragment(this, requestCode);
        itemDialog.show(Objects.requireNonNull(getFragmentManager()).beginTransaction(), TAG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_PACKET_ITEM && resultCode == Activity.RESULT_OK) {
            try {
                DataPacketItem result = Objects.requireNonNull(
                        (DataPacketItem) data.getSerializableExtra(TextDialogFragment.EXTRA_RESULT)
                );

                DataPacket dataPacket = binding.getDataPacket();
                List<DataPacketItem> dataPacketItems = packetItemAdapter.getListItems();

                dataPacketItems.add(result);

                updateDataPacket(dataPacket, dataPacketItems);

            } catch (Exception e) {
                Log.w(TAG, e.getMessage());
            }
        }else if(requestCode == RC_PACKET_DOCTOR && resultCode == Activity.RESULT_OK){
            try {
                DataPacketItem result = Objects.requireNonNull(
                        (DataPacketItem) data.getSerializableExtra(TextDialogFragment.EXTRA_RESULT)
                );

                DataPacket dataPacket = binding.getDataPacket();
                List<DataPacketItem> dataPacketItems = packetItemAdapter.getListItems();

                dataPacket.setDoctorId(result.getValue());
                dataPacket.setDoctorName(result.getDisplayValue());

                updateDataPacket(dataPacket, dataPacketItems);

            } catch (Exception e) {
                Log.w(TAG, e.getMessage());
            }
        }
    }

    private void initialisePacketItemList(RecyclerView packetItemList) {
        packetItemAdapter = new PacketItemAdapter(packetItem -> {});
        packetItemList.setLayoutManager(new LinearLayoutManager(requireContext()));
        packetItemList.setAdapter(packetItemAdapter);

        SwipeDeleteAction swipeDeleteAction = new SwipeDeleteAction(requireContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                packetItemAdapter.removeAt(viewHolder.getAdapterPosition());
                updateDataPacket(binding.getDataPacket(), packetItemAdapter.getListItems());
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeDeleteAction);
        itemTouchHelper.attachToRecyclerView(packetItemList);
    }

    private void updatePacketBinding(DataPacket newPacket) {
        List<DataPacketItem> dataPacketItems = DataPacketItemManager.breakDownDataPacket(newPacket);
        packetItemAdapter.replaceListItems(dataPacketItems);

        updateAttachmentDisplayText(newPacket.getDocumentReferences());

    }

    private void updateDataPacket(DataPacket dataPacket, List<DataPacketItem> packetItems) {
        DataPacket newDataPacket = DataPacketItemManager.buildDataPacket(dataPacket, packetItems);

        viewModel.updateDataPacket(newDataPacket).observe( this, result -> {
            if(result != null && handleFirestoreResult(result) && result.getResource()){
                Toast.makeText(requireContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateAttachmentDisplayText(List<String> documentReferences) {
        if(documentReferences == null || documentReferences.isEmpty()){
            return;
        }

        for(String documentReference : documentReferences){
            viewModel.getFileMetaData(documentReference).observe(this, storageMetadata -> {
                if(storageMetadata != null && handleFirestoreResult(storageMetadata)){
                    List<DataPacketItem> newItems = packetItemAdapter.getListItems();

                    for(DataPacketItem dataPacketItem : newItems){
                        if(dataPacketItem.getValue().equals(documentReference)){
                            dataPacketItem.setDisplayValue(storageMetadata.getResource().getName());
                        }
                    }

                    packetItemAdapter.replaceListItems(newItems);
                }
            });
        }
    }
}
