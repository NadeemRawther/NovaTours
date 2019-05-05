package com.nova.bitsi34gb.novatours;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {



    private String password;
    private String name ;
    private String adhaar ;
    private String gender;
    private String maritalstatus ;
    private String dateofbirth;
    private String Earnings ;
    private String left ;
    private String right;
    private String rank;
    private String parent;
    private String phoneno;
    private String panno;
    private String dateofjoining;
    private String dateofpayment;

    public String getDateofjoining() {
        return dateofjoining;
    }

    public String getDateofpayment() {
        return dateofpayment;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getAdhaar() {
        return adhaar;
    }

    public String getGender() {
        return gender;
    }

    public String getMaritalstatus() {
        return maritalstatus;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public String getEarnings() {
        return Earnings;
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

    public String getParent() {
        return parent;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public String getPanno() {
        return panno;
    }

    public User(String password, String name, String adhaar, String gender, String maritalstatus, String dateofbirth, String earnings, String left, String right, String rank, String parent, String phoneno, String panno,String dateofjoining,String dateofpayment) {
        this.password = password;
        this.name = name;
        this.adhaar = adhaar;
        this.gender = gender;
        this.maritalstatus = maritalstatus;
        this.dateofbirth = dateofbirth;
        Earnings = earnings;
        this.left = left;
        this.right = right;
        this.rank = rank;
        this.parent = parent;
        this.panno = panno;
        this.phoneno = phoneno;
        this.dateofjoining = dateofjoining;
        this.dateofpayment = dateofpayment;

    }

    public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }




}
