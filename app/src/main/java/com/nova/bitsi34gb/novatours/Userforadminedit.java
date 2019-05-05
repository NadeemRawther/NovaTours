package com.nova.bitsi34gb.novatours;

public class Userforadminedit {

   private String name;
    private String adhaar;
    private String panno;
    private  String phoneno;
    private String dateofbirth;
    private String gender;
    private String rank;
    private String maritalstatus;
    private String parent;
    private String earnings;
    private String dateofpayment;
    private String dateofjoining;
    private String left;
    private String right;

    public Userforadminedit(String name, String adhaar, String panno, String phoneno, String dateofbirth, String gender, String rank, String maritalstatus, String parent, String earnings, String dateofpayment, String dateofjoining, String left, String right) {
        this.name = name;
        this.adhaar = adhaar;
        this.panno = panno;
        this.phoneno = phoneno;
        this.dateofbirth = dateofbirth;
        this.gender = gender;
        this.rank = rank;
        this.maritalstatus = maritalstatus;
        this.parent = parent;
        this.earnings = earnings;
        this.dateofpayment = dateofpayment;
        this.dateofjoining = dateofjoining;
        this.left = left;
        this.right = right;
    }

    public String getName() {
        return name;
    }

    public String getAdhaar() {
        return adhaar;
    }

    public String getPanno() {
        return panno;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public String getGender() {
        return gender;
    }

    public String getRank() {
        return rank;
    }

    public String getMaritalstatus() {
        return maritalstatus;
    }

    public String getParent() {
        return parent;
    }

    public String getEarnings() {
        return earnings;
    }

    public String getDateofpayment() {
        return dateofpayment;
    }

    public String getDateofjoining() {
        return dateofjoining;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }
}
