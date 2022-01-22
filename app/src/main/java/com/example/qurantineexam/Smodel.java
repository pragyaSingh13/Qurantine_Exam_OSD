package com.example.qurantineexam;

public class Smodel {
    String name,rollno,imageuri;

    public Smodel(String name, String rollno, String imageuri) {
        this.name = name;
        this.rollno = rollno;
        this.imageuri = imageuri;
    }

    public Smodel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }
}
