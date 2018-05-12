package com.example.alonkatz.promdate;
import java.util.ArrayList;
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
    private boolean isHeterosexual;
    private String state;
    private String city;
    private String school;
    private String district;
    private String description;
    ArrayList<String> likedUsers;
    //
    private ImageView pic;


    public User(int id,String name,boolean male, Date dateOfBirth ,int ageRange, boolean isHeterosexual, String state, String city, String school, String district, String description ){
        this.id=id;
        this.name=name;
        this.dateOfBirth=dateOfBirth;
        this.isHeterosexual=isHeterosexual;
        this.state=state;
        this.city=city;
        this.school=school;
        this.district=district;
        this.description=description;
    }



}
