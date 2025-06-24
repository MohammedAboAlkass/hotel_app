package com.example.finalproject_ucas.adapter;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private final int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = space;

        // عشان ما تحط مسافة فوق أول عنصر
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space;
        }
    }
}
