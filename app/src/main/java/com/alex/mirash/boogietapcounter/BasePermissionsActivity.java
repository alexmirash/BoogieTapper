package com.alex.mirash.boogietapcounter;

import android.content.pm.PackageManager;
import android.util.Log;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Mirash
 */
public abstract class BasePermissionsActivity extends AppCompatActivity {
    private static final String TAG = "PermissionsCheck";
    private int requestCode;

    private SparseArray<ResultCallback> callbacks = new SparseArray<>();
    private Queue<PermissionRequest> pendingRequests = new LinkedList<>();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        callbacks.clear();
        pendingRequests.clear();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult requestCode = " + requestCode);
        ResultCallback callback = callbacks.get(requestCode);
        if (callback != null) {
            boolean requestResult = true;
            callbacks.remove(requestCode);
            if (grantResults.length > 0) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        requestResult = false;
                        break;
                    }
                }
            } else {
                requestResult = false;
            }
            Log.d(TAG, "onRequestPermissionsResult " + requestResult + ": " + Arrays.toString(permissions)
                    + "\npendingSize = " + pendingRequests.size());
            callback.onResult(requestResult);
            if (callbacks.size() == 0) {
                PermissionRequest request = pendingRequests.poll();
                if (request != null) {
                    performTaskOnPermissionResultInternal(request.getCallback(), request.getPermissions());
                }
            }
        }
    }

    public void requestPermissionsIfNecessary(String... permissions) {
        performTaskOnPermissionsGranted(null, permissions);
    }

    public boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void performTaskOnPermissionsGranted(ResultCallback callback, String... permissions) {
        if (permissions == null) return;
        if (callbacks.size() == 0) {
            performTaskOnPermissionResultInternal(callback, permissions);
        } else {
            pendingRequests.add(new PermissionRequest(callback, permissions));
        }
    }

    private void performTaskOnPermissionResultInternal(ResultCallback callback, String... permissions) {
        Log.d(TAG, "performTaskOnPermissionResultInternal: " + Arrays.toString(permissions));
        List<String> requestPermissions = new ArrayList<>(permissions.length);
        for (String permission : permissions) {
            if (!hasPermission(permission)) {
                requestPermissions.add(permission);
            }
        }
        if (requestPermissions.isEmpty()) {
            if (callback != null) {
                callback.onResult(true);
            }
        } else {
            int requestCode = getPermissionRequestCode();
            callbacks.put(requestCode, callback == null ? (ResultCallback) value -> {
            } : callback);
            ActivityCompat.requestPermissions(this, requestPermissions.toArray(new String[0]), requestCode);
        }
    }

    private int getPermissionRequestCode() {
        requestCode++;
        if (requestCode >= 255) requestCode = 0;
        return requestCode;
    }
}

