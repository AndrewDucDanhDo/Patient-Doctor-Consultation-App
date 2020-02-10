package com.group4.patientdoctorconsultation.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.group4.patientdoctorconsultation.common.GlideApp;

public class BindingAdapters {

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, int resource){
        imageView.setImageResource(resource);
    }

    @SuppressLint("CheckResult")
    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();

        GlideApp.with(context)
                .load(url)
                .into(imageView);
    }

}