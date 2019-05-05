package com.nova.bitsi34gb.novatours;

import java.io.Serializable;

public class NameAndEarnings implements Serializable {
    private String name;
    private String userid;
    private String left;
    private String right;
    private String rank;

    public NameAndEarnings( String name, String userid, String left, String right, String rank) {
        this.name = name;
        this.userid = userid;
        this.left = left;
        this.right = right;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public String getUserid() {
        return userid;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    public String getRank() {
        return rank;
    }

  /*  @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }*/
}
