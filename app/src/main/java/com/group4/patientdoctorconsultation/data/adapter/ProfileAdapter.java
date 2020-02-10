package com.group4.patientdoctorconsultation.data.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.group4.patientdoctorconsultation.R;
import com.group4.patientdoctorconsultation.common.BindingAdapter;
import com.group4.patientdoctorconsultation.common.ClickListener;
import com.group4.patientdoctorconsultation.data.model.Profile;
import com.group4.patientdoctorconsultation.databinding.ItemProfileBinding;

public class ProfileAdapter extends BindingAdapter<Profile, ItemProfileBinding> {

    public ProfileAdapter(ClickListener<Profile> clickListener){
        super(clickListener);
    }

    @Override
    protected ItemProfileBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        final ItemProfileBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_profile, parent, false);
        binding.getRoot().setOnClickListener(v -> {
            replaceListItems(getListItems());
            Profile chosen = binding.getProfile();
            if (chosen != null) {
                binding.getRoot().setBackgroundResource(R.color.colorSelectedOffWhite);
                clickListener.onClicked(chosen);
            }
        });
        return binding;
    }

    @Override
    protected void bind(ItemProfileBinding binding, Profile profile) {
        binding.setProfile(profile);
    }

}
