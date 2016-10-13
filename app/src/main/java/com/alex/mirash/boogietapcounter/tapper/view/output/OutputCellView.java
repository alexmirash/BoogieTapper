package com.alex.mirash.boogietapcounter.tapper.view.output;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alex.mirash.boogietapcounter.R;

/**
 * @author Mirash
 */

public class OutputCellView extends FrameLayout {
    protected TextView labelView;
    protected TextView valueView;

    public OutputCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, getLayoutId(), this);
        labelView = (TextView) findViewById(R.id.output_label);
        valueView = (TextView) findViewById(R.id.output_value);
    }

    protected int getLayoutId() {
        return R.layout.view_outout_cell_main;
    }

    public void setValueText(String valueText) {
        valueView.setText(valueText);
    }

    public void setValue() {

    }

    public void setLabelText(String labelText) {
        labelView.setText(labelText);
    }

    public void highlight() {
        setValueText("!" + valueView.getText() + "!");
    }
}
