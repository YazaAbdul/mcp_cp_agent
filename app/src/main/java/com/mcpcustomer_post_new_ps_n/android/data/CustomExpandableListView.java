package com.mcpcustomer_post_new_ps_n.android.data;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

/**
 * Created by venkei on 15-Mar-19.
 */
public class CustomExpandableListView extends ExpandableListView {

    boolean expanded = false;

    public CustomExpandableListView(Context context) {
        super(context);
    }

    public CustomExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomExpandableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean isExpanded() {
        return expanded;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        try {
            if (isExpanded()) {

                try {
                    int expandSpec = MeasureSpec.makeMeasureSpec(
                            Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
                    try {
                        super.onMeasure(widthMeasureSpec, expandSpec);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ViewGroup.LayoutParams params = getLayoutParams();
                    params.height = getMeasuredHeight();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}


