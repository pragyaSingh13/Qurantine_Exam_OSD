package com.example.qurantineexam;

public class Student {
    String name,rollno;

    public Student(String name, String rollno) {
        this.name = name;
        this.rollno = rollno;
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
}
