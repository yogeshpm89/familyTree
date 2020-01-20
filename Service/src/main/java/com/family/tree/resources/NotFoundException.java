package com.family.tree.resources;

public class NotFoundException extends Exception {
    private int code;
    public NotFoundException (int code, String msg) {
        super(msg);
        this.code = code;
    }
}
