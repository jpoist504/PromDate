package com.example.alonkatz.promdate;


import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.EditText;
import  android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SwipingFragment extends Fragment {

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String currentUserID = currentUser.getUid();
    DatabaseReference myRef;
    ArrayList<User> allUsers = new ArrayList<User>();
    TextView userName;
    View view;

    //displayedUserName should hold the name of the user that is on the screen, not the logged in user.
    String displayedUserName;
    //displayedUserID should hold the userID of the user that is on the screen, not the logged in user.
    String displayedUserID;

    public SwipingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    //displayedUserID="0MqovCsEe6OlKIxWZlRpXaCQA9g2";
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_swiping, container, false);
      //show first user

        displayedUserID="";
        displayedUserName="";
        return view;
    }


    //This is how you put a matchedUser into the database
    public void onMatch(){
        myRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserID).child("matched_users");
        myRef.push();
        MatchedUser match = new MatchedUser(displayedUserName, displayedUserID);
        myRef.setValue(match);

    }
    public void swipeRight(View view){

        onMatch();
        showNextUser();
    }
    public void swipeLeft(View view){
       showNextUser();
    }
    public void showNextUser(){
        //finds a new user id
        displayedUserID="";

        myRef = FirebaseDatabase.getInstance().getReference().child("users").child(displayedUserID);
       myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             //   listOfUsers = dataSnapshot.getValue(ArrayList.class);
                //do what you want with the email
                User user = dataSnapshot.getValue(User.class);
                displayedUserID=dataSnapshot.getValue(ArrayList.class).toString();
                displayedUserName=user.getFirstName();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        userName = (EditText) view.findViewById(R.id.userName);
        userName.setText( displayedUserName);


    }
}
