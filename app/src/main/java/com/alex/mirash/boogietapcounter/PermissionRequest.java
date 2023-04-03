package com.alex.mirash.boogietapcounter;

/**
 * @author Mirash
 */
public class PermissionRequest {
    private final String[] permissions;
    private final ResultCallback callback;

    public PermissionRequest(ResultCallback callback, String... permissions) {
        this.callback = callback;
        this.permissions = permissions;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public ResultCallback getCallback() {
        return callback;
    }
}
