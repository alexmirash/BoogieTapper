package com.alex.mirash.boogietapcounter.tapper.view.output;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alex.mirash.boogietapcounter.R;
import com.alex.mirash.boogietapcounter.tapper.tool.Utils;

/**
 * @author Mirash
 */

public class OutputCellView extends FrameLayout implements Highlightable {
    protected TextView labelView;
    protected TextView valueView;

    protected ValueFormat format;

    public OutputCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, getLayoutId(), this);
        labelView = findViewById(R.id.output_label);
        valueView = findViewById(R.id.output_value);

        format = new ValueFormat(getPrecision());
    }

    protected int getPrecision() {
        return 2;
    }

    protected int getLayoutId() {
        return R.layout.view_outout_cell_main;
    }

    public void setValueText(String valueText) {
        valueView.setText(valueText);
    }

    @SuppressLint("DefaultLocale")
    public void setValue(float value) {
        setValueText(String.format(format.getStrFormat(), Utils.round(value, format.getPrecision())));
    }

    public void setLabelText(String labelText) {
        labelView.setText(labelText);
    }

    @Override
    public void setHighlighted(boolean highlighted) {
        valueView.setTextColor(ContextCompat.getColor(getContext(),
                highlighted ? R.color.colorAccent : R.color.output_text_color));
    }

    protected static class ValueFormat {
        private int precision;
        private String strFormat;

        public ValueFormat(int precision) {
            this.precision = precision;
            strFormat = "%." + precision + "f";
        }

        public int getPrecision() {
            return precision;
        }

        public String getStrFormat() {
            return strFormat;
        }
    }
}
