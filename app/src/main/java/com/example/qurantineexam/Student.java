package com.example.qurantineexam;

public class Student {
    String name,rollno,image;

    public Student(String name, String rollno, String image) {
        this.name = name;
        this.rollno = rollno;
        this.image = image;
    }

    public Student() {
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

    public String getImage(){
        return image;
    }
    public void setImage(String image){
        this.image = image;
    }
}
