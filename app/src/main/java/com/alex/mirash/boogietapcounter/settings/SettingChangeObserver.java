package com.alex.mirash.boogietapcounter.settings;

/**
 * @author Mirash
 */

public interface SettingChangeObserver<T> {
    void onSettingChanged(T setting);
}
