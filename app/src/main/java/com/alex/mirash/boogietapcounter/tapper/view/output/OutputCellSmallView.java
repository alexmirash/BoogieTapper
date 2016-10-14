package com.alex.mirash.boogietapcounter.tapper.view.output;

import android.content.Context;
import android.util.AttributeSet;

import com.alex.mirash.boogietapcounter.R;

/**
 * @author Mirash
 */

public class OutputCellSmallView extends OutputCellView {

    public OutputCellSmallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        format = 3;
    }

    protected int getLayoutId() {
        return R.layout.view_outout_cell_small;
    }
}
