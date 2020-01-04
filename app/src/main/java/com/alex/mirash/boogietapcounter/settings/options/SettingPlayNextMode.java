package com.alex.mirash.boogietapcounter.settings.options;

import android.content.res.ColorStateList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.alex.mirash.boogietapcounter.R;
import com.google.android.material.button.MaterialButton;

/**
 * @author Mirash
 */

public enum SettingPlayNextMode {
    NEXT_FILE {
        @Override
        public void updateButton(@NonNull MaterialButton button) {
            button.setIconResource(R.drawable.ic_repeat_black_24dp);
            button.setIconTint(ColorStateList.valueOf(ContextCompat.getColor(button.getContext(), R.color.colorAccent)));
        }
    },
    REPEAT {
        @Override
        public void updateButton(@NonNull MaterialButton button) {
            button.setIconResource(R.drawable.ic_repeat_one_black_24dp);
            button.setIconTint(ColorStateList.valueOf(ContextCompat.getColor(button.getContext(), R.color.colorAccent)));
        }
    },
    NONE {
        @Override
        public void updateButton(@NonNull MaterialButton button) {
            button.setIconResource(R.drawable.ic_repeat_black_24dp);
            button.setIconTint(ColorStateList.valueOf(ContextCompat.getColor(button.getContext(), R.color.colorDisabled)));
        }
    };

    @NonNull
    public SettingPlayNextMode next() {
        SettingPlayNextMode[] values = values();
        int ordinal = ordinal();
        if (ordinal == values.length - 1) {
            return values[0];
        }
        return values[ordinal + 1];
    }

    public static SettingPlayNextMode getValue(int position) {
        SettingPlayNextMode[] values = values();
        if (position >= 0 && position < values.length) {
            return values[position];
        } else return getDefaultValue();
    }

    public static SettingPlayNextMode getDefaultValue() {
        return NEXT_FILE;
    }

    public abstract void updateButton(@NonNull MaterialButton playNextModeButton);
}
