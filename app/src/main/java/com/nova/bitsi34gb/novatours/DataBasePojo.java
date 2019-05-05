package com.nova.bitsi34gb.novatours;

public class DataBasePojo {
    private int Accountno ;
    private int Adhaar;
    private String Bank;
    private String BankName;
    private String Branch;
    private String DateofBirth;
    private int Earnings;
    private String Gender;
    private int IFScCode;
    private String maritalstatus;
    private String Name;
    private String parent;
    private String left;
    private String right;

    public DataBasePojo(int accountno, int adhaar, String bank, String bankName, String branch, String dateofBirth, int earnings, String gender, int IFScCode, String maritalstatus, String name, String parent, String left, String right) {
        Accountno = accountno;
        Adhaar = adhaar;
        Bank = bank;
        BankName = bankName;
        Branch = branch;
        DateofBirth = dateofBirth;
        Earnings = earnings;
        Gender = gender;
        this.IFScCode = IFScCode;
        this.maritalstatus = maritalstatus;
        Name = name;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    public int getAccountno() {
        return Accountno;
    }

    public int getAdhaar() {
        return Adhaar;
    }

    public String getBank() {
        return Bank;
    }

    public String getBankName() {
        return BankName;
    }

    public String getBranch() {
        return Branch;
    }

    public String getDateofBirth() {
        return DateofBirth;
    }

    public int getEarnings() {
        return Earnings;
    }

    public String getGender() {
        return Gender;
    }

    public int getIFScCode() {
        return IFScCode;
    }

    public String getMaritalstatus() {
        return maritalstatus;
    }

    public String getName() {
        return Name;
    }

    public String getParent() {
        return parent;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }
}
