package com.example.cspeir.collegeapp;

import java.util.ArrayList;

/**
 * Created by shunt on 10/20/2017.
 */

public class Family {
    private final static String TAG = Family.class.getName();
    private ArrayList<FamilyMember> mFamily;
    private static Family sFamily;

    private Family() {
        mFamily = new ArrayList<>();
        Guardian mom = new Guardian("my", "mom");
        Guardian dad = new Guardian("my", "dad");
        Sibling sister = new Sibling("my", "sister");
        mFamily.add(mom);
        mFamily.add(dad);
        mFamily.add(sister);

    }

    public static Family get() {
        if (sFamily == null) {
            sFamily = new Family();
        }
        return sFamily;
    }

    public ArrayList<FamilyMember> getFamily() {
        return mFamily;
    }

    public void setFamily(ArrayList<FamilyMember> familyList) {
        mFamily = familyList;
    }

    public void addFamilyMember(FamilyMember familymember) {
        mFamily.add(familymember);
    }
    public void deleteFamilyMember(FamilyMember familymember){
        mFamily.remove(familymember);
    }

}
