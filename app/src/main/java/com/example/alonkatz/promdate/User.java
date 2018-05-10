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

}
