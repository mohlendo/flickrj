/*
 * Copyright (c) 2005 Aetrion LLC.
 */

package com.aetrion.flickr.auth;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum class for Permissions defined for auth results.
 *
 * @author Anthony Eden
 */
public class Permission {

    public static final int NONE_TYPE = 0;
    public static final int READ_TYPE = 1;
    public static final int WRITE_TYPE = 2;
    public static final int DELETE_TYPE = 3;

    public static final Permission NONE = new Permission(NONE_TYPE);
    public static final Permission READ = new Permission(READ_TYPE);
    public static final Permission WRITE = new Permission(WRITE_TYPE);
    public static final Permission DELETE = new Permission(DELETE_TYPE);

    private static final Map stringToPermissionMap = new HashMap();
    static {
        stringToPermissionMap.put("none", NONE);
        stringToPermissionMap.put("read", READ);
        stringToPermissionMap.put("write", WRITE);
        stringToPermissionMap.put("delete", DELETE);
    }

    private int type;

    private Permission(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    /**
     * Convert the permission String to a Permission object.
     *
     * @param permission The permission String
     * @return The Permission object
     */
    public static Permission fromString(String permission) {
        return (Permission) stringToPermissionMap.get(permission.toLowerCase());
    }

    public String toString() {
        switch (type) {
            case NONE_TYPE: return "none";
            case READ_TYPE: return "read";
            case WRITE_TYPE: return "write";
            case DELETE_TYPE: return "delete";
            default:
                throw new IllegalStateException("Unsupported type: " + type);
        }
    }


}
