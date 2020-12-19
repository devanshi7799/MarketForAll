package com.example.mfa;

public class Member {

    String FirstName;
    String SecondName;
    String Email;
    String Password;
    String type;

    String Rfname;
    String Remail;
    String Rsname;
    String Rphone;
    String Rgst;
    String Rgoods;
    String Rorgname;
    String Rgoodstype;


    public Member(String type, String rfname, String remail, String rsname, String rphone, String rgst, String rgoods, String rorgname,String rgoodstype) {
        this.type = type;
        Rfname = rfname;
        Remail = remail;
        Rsname = rsname;
        Rphone = rphone;
        Rgst = rgst;
        Rgoods = rgoods;
        Rorgname = rorgname;
        Rgoodstype = rgoodstype;
    }

    public String getRgoodstype() {
        return Rgoodstype;
    }

    public void setRgoodstype(String rgoodstype) {
        Rgoodstype = rgoodstype;
    }

    public String getRsname() {
        return Rsname;
    }

    public void setRsname(String rsname) {
        Rsname = rsname;
    }

    public String getRphone() {
        return Rphone;
    }

    public void setRphone(String rphone) {
        Rphone = rphone;
    }

    public String getRgst() {
        return Rgst;
    }

    public void setRgst(String rgst) {
        Rgst = rgst;
    }

    public String getRgoods() {
        return Rgoods;
    }

    public void setRgoods(String rgoods) {
        Rgoods = rgoods;
    }

    public String getRorgname() {
        return Rorgname;
    }

    public void setRorgname(String rorgname) {
        Rorgname = rorgname;
    }

    public String getRfname() {
        return Rfname;
    }

    public void setRfname(String rfname) {
        Rfname = rfname;
    }

    public String getRemail() {
        return Remail;
    }

    public void setRemail(String remail) {
        Remail = remail;
    }

    public Member(String firstName, String secondName, String email, String password, String type) {
        FirstName = firstName;
        SecondName = secondName;
        Email = email;
        Password = password;
        this.type = type;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getSecondName() {
        return SecondName;
    }

    public void setSecondName(String secondName) {
        SecondName = secondName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
