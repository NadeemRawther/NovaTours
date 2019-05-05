package com.nova.bitsi34gb.novatours;

public class NodeForBinary {
    private String user;
    private String left;
    private String right;

    public NodeForBinary(String user, String left, String right) {
        this.user = user;
        this.left = left;
        this.right = right;
    }

    public String getUser() {
        return user;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }
}
