package com.example.d3ll.firebaseproject.fragment_class;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d3ll.firebaseproject.R;

public class Divider extends RecyclerView.ItemDecoration {
    private Drawable drawable;
    private int orientation;
    private  Context context;
    private  int margin;
    public Divider(Context context,int orientation, int margin)
    {
        this.context=context;
            this.drawable=context.getDrawable(R.drawable.divider);
            if(orientation!= LinearLayoutManager.VERTICAL)
            {
                throw new IllegalArgumentException("Recycler View Linear layout manager should be Vertical");
            }
           this.orientation=orientation;
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if(orientation==LinearLayoutManager.VERTICAL)
        {
            drawDivider(c,parent,state);
        }

    }

    private void drawDivider(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int top,bottom,right,left;
        left=parent.getPaddingLeft();
        right=parent.getWidth()-parent.getPaddingRight();
        int child=parent.getChildCount();

        for (int i = 0; i <child ; i++) {
                View current=parent.getChildAt(i);
                RecyclerView.LayoutParams params=(RecyclerView.LayoutParams) current.getLayoutParams();
                top=current.getBottom()+params.bottomMargin;
                bottom=top+drawable.getIntrinsicHeight();
                drawable.setBounds(left + dpToPx(margin), top, right - dpToPx(margin), bottom);
                drawable.draw(c);
        }
    }
    private int dpToPx(int dp) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.set(0, 0, 0, drawable.getIntrinsicHeight());

    }
}
