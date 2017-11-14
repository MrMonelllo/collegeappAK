package com.example.cspeir.collegeapp;

/**
 * Created by shunt on 10/6/2017.
 */

public abstract class FamilyMember extends ApplicantData {
    private String firstName;
    private String lastName;

    public static final String EXTRA_RELATION = "org.pltw.examples.collegeapp.relation";
    public static final String EXTRA_INDEX = "org.pltw.examples.collegeapp.index";

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public FamilyMember(){
        firstName="Jeffery";
        lastName = "Hunt";
    }
    public FamilyMember (String first, String last){
        this.firstName = first;
        this.lastName = last;
    }
    public boolean equals(Object o) {
        if ((o instanceof Guardian) && (this instanceof Guardian)) {
            Guardian g = (Guardian) o;
            if (g.getFirstName() == this.getFirstName() && g.getLastName() == this.getLastName())
                return true;
        }
        else if ((o instanceof Sibling && this instanceof Sibling)) {
            Sibling s = (Sibling) o;
            if (s.getFirstName() == this.getFirstName() && s.getLastName() == this.getLastName())
                return true;
        }
        return false;
    }

}

