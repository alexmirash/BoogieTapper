package com.alex.mirash.boogietapcounter.mp3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alex.mirash.boogietapcounter.settings.unit.UnitValue;
import com.mpatric.mp3agic.Mp3File;

import java.io.File;

/**
 * @author Mirash
 */
public interface ISaveStrategy {
    File saveBpm(@NonNull File baseMp3File, @Nullable Mp3File mp3file, @NonNull UnitValue unitValue);
}
