package com.example.cspeir.collegeapp;

/**
 * Created by shunt on 10/6/2017.
 */

public class Guardian extends FamilyMember {
    private String occupation;
    public Guardian(String first, String last){
        super(first, last);
        occupation= "unknown";
    }
    public Guardian(){
        super();
        occupation= "unknown";

    }
    public Guardian (String first, String last, String occupation){
        super(first,last);
        this.occupation = occupation;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    @Override
    public String toString(){
        return "Guardian: " + getFirstName() + " " + getLastName() + "\nOccupation: " + getOccupation();
    }
}
