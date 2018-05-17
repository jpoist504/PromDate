package com.example.alonkatz.promdate;
import java.util.ArrayList;
import java.util.Date;
import android.widget.ImageView;

/**
 * @author Ben
 * Represents a user
 */
public class User  {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isMale;
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

    //Reflects what a user looks like stored in firebase
    public User(String id, String firstName, String lastName, String email, boolean isMale){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isMale = isMale;
    }


    public User(String id,String firstName, String lastName,boolean male, Date dateOfBirth ,int ageRange, boolean isHeterosexual, String state, String city, String school, String district, String description ){
        this.id=id;
        this.firstName= firstName;
        this.lastName = lastName;
        this.dateOfBirth=dateOfBirth;
        this.isHeterosexual=isHeterosexual;
        this.state=state;
        this.city=city;
        this.school=school;
        this.district=district;
        this.description=description;
    }

    public String getId(){return id;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public String getEmail(){return email;}

    /**
     *
     * @return returns true if male false if female
     */
    public boolean getGender(){return isMale;}


}
