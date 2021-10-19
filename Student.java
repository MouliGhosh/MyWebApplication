package com.myproject.myproject.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(name = "students")
@Data

public class Student {

    @Id//for primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//for autoincrement
    private int id;
    private String image;
    @Size(min=2,message ="name should be more than 2 character")
    private String name;
    @Size(max=10,message = "The contact no be 10 numbers")
    private String contact;
    @Size(min=2,message ="The mail should be than 2 character and should contain @ symbol")
    private String email;
    @Size(min=2,message ="The Address should be more than 2 character")
    private String address;
    @Size(min=2,message="The course must be more than 2 character")
    private String course;
    private  String marks10;
    private String yop10;
    private String marks12;
    private String yop12;
    private String marksdiploma;
    private String yopdiploma;
    private String entranceexam;
    private String rank;
    private String payment;

}