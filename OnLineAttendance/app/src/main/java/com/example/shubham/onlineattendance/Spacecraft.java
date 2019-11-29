package com.example.shubham.onlineattendance;

/**
 * Created by shubham on 19/03/2017.
 */

public class Spacecraft {
    int roll;
    String Rolll,fname,lname;
    String baddress;

    public int getRoll() {
        return this.roll;
    }

    public void getRoll(int roll) {
        this.roll = roll;
    }
    public String getRolll() {
        return Rolll;
    }

    public void setRolll(String Rolll) {
        this.Rolll = Rolll;
    }
    public String getName() {
        return fname;
    }

    public void setName(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getBaddress() {
         return baddress;
    }

    public void setBaddress(String baddress) {
        this.baddress = baddress;
    }
}
