package com.example.alonkatz.promdate;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class SwipingFragment extends Fragment implements View.OnClickListener {
    int index=0;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String currentUserID = currentUser.getUid();
    DatabaseReference myRef;
    ArrayList<User> allUsers = new ArrayList<User>();
    ArrayList<MatchedUser> matchedUserIdList = new ArrayList<MatchedUser>();
    TextView userName;
    View view;
    List<String> userIdList = new ArrayList();
    //displayedUserName should hold the name of the user that is on the screen, not the logged in user.
    String displayedUserName="";
    //displayedUserID should hold the userID of the user that is on the screen, not the logged in user.
    String displayedUserID="";

    public SwipingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_swiping, container, false);
        ImageButton b = (ImageButton) view.findViewById(R.id.swipeRight);
        b.setOnClickListener(this);
        ImageButton c = (ImageButton) view.findViewById(R.id.swipeLeft);
        c.setOnClickListener(this);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference().child("users");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot==null)return;
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        userIdList.add(postSnapshot.getKey());
                            User user = postSnapshot.getValue(User.class);
                            allUsers.add(user);
                        Log.i("User", "" + userIdList.toString());
                    }

                   // User user = dataSnapshot.getValue(User.class);

                }
//
//                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
//displayedUserID=allUsers.get(0).getId();
//displayedUserName=allUsers.get(0).getFirstName()+allUsers.get(0).getLastName();
        //showNextUser();
        return view;
    }


    //This is how you put a matchedUser into the database
    public void onMatch() {

        myRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserID).child("matched_users");
        MatchedUser match = new MatchedUser(displayedUserName, displayedUserID);
        myRef.push().setValue(match);
    }

    public void swipeRight() {
       onMatch();
        showNextUser();

    }
    public void swipeLeft(){

    showNextUser();
    }
    public void showNextUser()
    {
        // Delete all users with null fields, build user DB up.

        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference myRef5 = database2.getReference().child("users").child(currentUserID).child("matched_users");
        myRef5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot==null)return;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    MatchedUser user = postSnapshot.getValue(MatchedUser.class);
                    matchedUserIdList.add(user);
                    Log.i("MatchedUser", "" + user.getUserID());
                }

                // User user = dataSnapshot.getValue(User.class);

            }
            //
//                @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        userName = (TextView) view.findViewById(R.id.userName);

        displayedUserName=allUsers.get(index).getFirstName()+allUsers.get(index).getLastName();
        displayedUserID=allUsers.get(index).getId();
        userName.setText(displayedUserName);

        //Make sure we don't show matched user
        User currUser = allUsers.get(index);
        for(MatchedUser x : matchedUserIdList){
            if(x.getUserID().equals(currUser.getId())){
                if(index < allUsers.size() - 1){
                    index++;
                } else {
                    index = 0;
                }
                Log.i("MatchedUser", "USERS ARE MATCHING" + currUser.getId());
                break;
            }
        }
        if(index<allUsers.size()-1)
        index++;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.swipeRight:
                swipeRight();
                break;
            case R.id.swipeLeft:
                swipeLeft();
                break;

                default:
                    break;
        }
    }
}
