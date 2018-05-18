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
    private String dateOfBirth;

    ArrayList<String> likedUsers;
    //
    private ImageView pic;

    public User (){

    }

    //Reflects what a user looks like stored in firebase
    public User(String id, String firstName, String lastName, String email, boolean isMale, String dateOfBirth){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isMale = isMale;
        this.dateOfBirth = dateOfBirth;
    }


    public User(String id,String firstName, String lastName,boolean male, String dateOfBirth, String email){
        this.id=id;
        this.firstName= firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth=dateOfBirth;

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
