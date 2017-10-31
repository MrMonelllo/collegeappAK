package com.example.cspeir.collegeapp;

/**
 * Created by shunt on 10/6/2017.
 */

public class Sibling  extends FamilyMember {
    public Sibling(String first, String last){
        super(first, last);
    }
    public String toString(){
        return "Guardian: " + getFirstName() + " " + getLastName();
    }
}
