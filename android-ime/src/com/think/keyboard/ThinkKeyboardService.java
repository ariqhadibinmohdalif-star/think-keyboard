package com.think.keyboard;

import android.inputmethodservice.InputMethodService;
import android.view.KeyEvent;
import android.view.inputmethodservice.InputConnection;
import android.util.Log;

public class ThinkKeyboardService extends InputMethodService {

    private static final String TAG = "ThinkKeyboard";
    private static final long HOLD_THRESHOLD_MS = 200; // 200ms hold threshold for mind tricks
    private long spaceKeyDownTime = 0;
    private boolean isHoldingSpacebar = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return super.onKeyDown(keyCode, event);

        // Intercepting the Mind Spacebar
        if (keyCode == KeyEvent.KEYCODE_SPACE) {
            if (event.getRepeatCount() == 0) {
                spaceKeyDownTime = System.currentTimeMillis();
                isHoldingSpacebar = true;
                Log.d(TAG, "Mind Spacebar down. Monitoring duration...");
            }
            return true; // Intercept event from active target apps
        }

        // If holding Spacebar down, intercept home row triggers for floating tools
        if (isHoldingSpacebar) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_C:
                    launchCalculatorOverlay();
                    return true;
                case KeyEvent.KEYCODE_R:
                    launchCalendarReminderOverlay();
                    return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return super.onKeyUp(keyCode, event);

        if (keyCode == KeyEvent.KEYCODE_SPACE) {
            isHoldingSpacebar = false;
            long duration = System.currentTimeMillis() - spaceKeyDownTime;

            // Short tap: output a standard space
            if (duration < HOLD_THRESHOLD_MS) {
                ic.commitText(" ", 1);
                Log.d(TAG, "Standard space committed.");
            } else {
                Log.d(TAG, "Layer function executed via held spacebar.");
            }
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    private void launchCalculatorOverlay() {
        Log.i(TAG, "Brain Trick: Activating instant floating calculator view.");
    }

    private void launchCalendarReminderOverlay() {
        Log.i(TAG, "Brain Trick: Activating frictionless calendar logger.");
    }
}

