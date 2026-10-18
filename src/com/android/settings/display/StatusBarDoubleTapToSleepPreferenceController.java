/*
 * Copyright (C) 2026 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.settings.display;

import android.content.Context;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;

/**
 * Controls the "double-tap the status bar to turn off the screen" switch.
 *
 * The value is stored in {@link Settings.System#STATUS_BAR_DOUBLE_TAP_TO_SLEEP} and defaults to
 * enabled.
 */
public class StatusBarDoubleTapToSleepPreferenceController extends BasePreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {

    private static final int DEFAULT_VALUE = 1;

    private Preference mPreference;

    public StatusBarDoubleTapToSleepPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        mPreference = screen.findPreference(getPreferenceKey());
        if (mPreference != null) {
            mPreference.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public void updateState(Preference preference) {
        ((TwoStatePreference) preference).setChecked(isGestureEnabled());
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Settings.System.putInt(mContext.getContentResolver(),
                Settings.System.STATUS_BAR_DOUBLE_TAP_TO_SLEEP, (Boolean) newValue ? 1 : 0);
        return true;
    }

    private boolean isGestureEnabled() {
        return Settings.System.getInt(mContext.getContentResolver(),
                Settings.System.STATUS_BAR_DOUBLE_TAP_TO_SLEEP, DEFAULT_VALUE) != 0;
    }
}
