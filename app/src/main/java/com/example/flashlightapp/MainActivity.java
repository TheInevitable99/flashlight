package com.example.flashlightapp;

import android.app.Activity;
        import android.content.Context;
        import android.content.pm.PackageManager;
        import android.hardware.camera2.CameraAccessException;
        import android.hardware.camera2.CameraManager;
        import android.os.Bundle;
        import android.widget.CompoundButton;
        import android.widget.ToggleButton;

public class MainActivity extends Activity{

    private CameraManager cameraManager;
    private String cameraId;
    private boolean isFlashlightOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton toggleButton = findViewById(R.id.toggleButton);
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException | NullPointerException e) {
            e.printStackTrace();
            // Handle errors here, like the device doesn't have a flash.
            return;
        }

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    turnOnFlashlight();
                } else {
                    turnOffFlashlight();
                }
            }
        });
    }

    private void turnOnFlashlight() {
        try {
            cameraManager.setTorchMode(cameraId, true);
            isFlashlightOn = true;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void turnOffFlashlight() {
        try {
            cameraManager.setTorchMode(cameraId, false);
            isFlashlightOn = false;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Turn off flashlight when the app is paused or closed.
        if (isFlashlightOn) {
            turnOffFlashlight();
        }
    }
}