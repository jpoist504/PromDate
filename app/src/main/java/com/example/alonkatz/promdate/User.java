package com.example.alonkatz.promdate;
import java.util.Date;
import android.widget.ImageView;

/**
 * @author Ben
 * Represents a user
 */
public class User  {
    private int id;
    private String name;
    private boolean male;
    private Date dateOfBirth;
    //Could be the number of years older or younger the user's age
    private int ageRange;
    //True for male and false for female
    private boolean sexualOrientation;
    private String state;
    private String city;
    private String school;
    private String district;
    private String description;
    //
    private ImageView pic;


    public User(int id,String name,boolean male, Date dateOfBirth ,int ageRange, boolean sexualOrientation, String state, String city, String school, String district, String description ){
        this.id=id;
        this.name=name;
        this.dateOfBirth=dateOfBirth;
        this.ageRange=ageRange;
        this.sexualOrientation=sexualOrientation;
        this.state=state;
        this.city=city;this.school=school;
        this.district=district;
        this.description=description;
    }
    public  User(int id){
     this.id=id;
    }

}
