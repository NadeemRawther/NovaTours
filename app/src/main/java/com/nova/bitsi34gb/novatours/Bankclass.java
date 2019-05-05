package com.nova.bitsi34gb.novatours;

public class Bankclass {


    private String accountno;
    private String bankname ;
    private String branch ;
    private String ifsccode;
    private String name;

    public Bankclass( String accountno, String bankname, String branch, String ifsccode, String name) {
        this.accountno = accountno;
        this.bankname = bankname;
        this.branch = branch;
        this.ifsccode = ifsccode;
        this.name = name;
    }

    public String getAccountno() {
        return accountno;
    }

    public String getBankname() {
        return bankname;
    }

    public String getBranch() {
        return branch;
    }

    public String getIfsccode() {
        return ifsccode;
    }

    public String getName() {
        return name;
    }
}
