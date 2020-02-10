package com.group4.patientdoctorconsultation.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.group4.patientdoctorconsultation.R;

import java.util.Objects;

public abstract class SwipeDeleteAction extends ItemTouchHelper.SimpleCallback {

    private Drawable deleteIcon;
    private int backgroundColor;

    protected SwipeDeleteAction(Context context) {
        super(0, ItemTouchHelper.LEFT);
        deleteIcon = Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.ic_delete_white_24dp));
        backgroundColor = Objects.requireNonNull(ContextCompat.getColor(context, R.color.colorDelete));
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        View itemView                       = viewHolder.itemView;
        ColorDrawable background            = new ColorDrawable();

        int listItemHeight                  = itemView.getBottom() - itemView.getTop();
        int listItemCurrentRightPosition    = itemView.getRight() + (int) dX;
        int deleteIconVerticalMargin        = (listItemHeight - deleteIcon.getIntrinsicHeight()) / 2;
        int deleteIconHorizontalMargin      = deleteIcon.getIntrinsicWidth();

        int deleteIconTopPosition           = itemView.getTop() + deleteIconVerticalMargin;
        int deleteIconBottomPosition        = deleteIconTopPosition + deleteIcon.getIntrinsicHeight();
        int deleteIconLeftPosition          = itemView.getRight() - deleteIconHorizontalMargin - deleteIcon.getIntrinsicWidth();
        int deleteIconRightPosition         = itemView.getRight() - deleteIconHorizontalMargin;

        background.setBounds(
                listItemCurrentRightPosition,
                itemView.getTop(),
                itemView.getRight(),
                itemView.getBottom()
        );
        background.setColor(backgroundColor);
        background.draw(c);

        deleteIcon.setBounds(
                deleteIconLeftPosition,
                deleteIconTopPosition,
                deleteIconRightPosition,
                deleteIconBottomPosition
        );
        deleteIcon.draw(c);

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
