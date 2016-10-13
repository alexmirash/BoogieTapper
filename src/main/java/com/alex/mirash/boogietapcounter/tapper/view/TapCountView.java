package com.alex.mirash.boogietapcounter.tapper.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.alex.mirash.boogietapcounter.R;
import com.alex.mirash.boogietapcounter.tapper.data.DataHolder;
import com.alex.mirash.boogietapcounter.tapper.tool.TapControlListener;

/**
 * @author Mirash
 */

public class TapCountView extends FrameLayout {
    private View tapButton;
    private DataOutputView dataOutputView;
    private DataDetailsView detailsView;

    private TapControlListener listener;

    public TapCountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_tap_counter, this);
        tapButton = findViewById(R.id.tap_button);
        dataOutputView = (DataOutputView) findViewById(R.id.data_output_view);
        detailsView = (DataDetailsView) findViewById(R.id.data_details_output_view);
        tapButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTap();
                }
            }
        });
    }

    public void setTapControlListener(TapControlListener listener) {
        this.listener = listener;
    }

    public void setData(DataHolder dataHolder) {
        dataOutputView.setData(dataHolder);
        detailsView.setData(dataHolder);
    }
}
