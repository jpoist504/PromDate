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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.google.firebase.storage.UploadTask;

/**
 * @author Ben
 * Represents the view in which you either "like" or "dislike" a presented user
 */
public class SwipingFragment extends Fragment implements View.OnClickListener {

    private int index;

    FirebaseUser currentUser;
    String currentUserID;
    DatabaseReference myRef;
    ArrayList<User> allUsers = new ArrayList<User>();
    ArrayList<MatchedUser> matchedUserIdList = new ArrayList<MatchedUser>();
    TextView userName;
    TextView description;
    private String currentUserName;
    List<String> userIdList = new ArrayList();
    //displayedUserName should hold the name of the user that is on the screen, not the logged in user.
    //displayedUserID should hold the userID of the user that is on the screen, not the logged in user.

    //StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("users").child(displayedUserID).child("Image");
    // ImageView image = (ImageView)view.findViewById(R.id.userImage);
    public SwipingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i("Create shit", "here bitch");

        View view = inflater.inflate(R.layout.fragment_swiping, container, false);
        ImageButton b = (ImageButton) view.findViewById(R.id.swipeRight);
        b.setOnClickListener(this);
        ImageButton c = (ImageButton) view.findViewById(R.id.nopeButton);
        c.setOnClickListener(this);

        index = 0;
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUserID = currentUser.getUid();



        userName = (TextView) view.findViewById(R.id.userName);
        description = (TextView) view.findViewById(R.id.description);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef5 = database.getReference().child("users").child(currentUserID).child("swipe_right");
        myRef5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("UserShit", dataSnapshot.toString());
                if (dataSnapshot == null) return;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    MatchedUser user = (MatchedUser) postSnapshot.getValue(MatchedUser.class);
                    matchedUserIdList.add(user);

                }

                // User user = dataSnapshot.getValue(User.class);

            }

            //
//                @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        int counter;

        DatabaseReference myRefAddUsers = database.getReference().child("users");
        myRefAddUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot == null) return;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    userIdList.add(postSnapshot.getKey());
                    User user = postSnapshot.getValue(User.class);
                    Log.i("INFOSHIT", user.getId());
                    Log.i("INFOSHIT", currentUser.getUid());
                    if (!user.getId().equals(currentUser.getUid())) {
                        boolean hasInList = false;
                        for (int i = 0; i < matchedUserIdList.size(); i++) {
                            if (matchedUserIdList.get(i).getUserID().equals(user.getId())) {
                                hasInList = true;
                                break;
                            }
                        }
                        if (!hasInList) {
                            allUsers.add(user);
                            Log.i("More Shit", allUsers.toString());
                        }
                    } else {
                        currentUserName = user.getFirstName() + " " + user.getLastName();
                    }
                    Log.i("User", "" + userIdList.toString());
                }
                Log.i("More Shit", allUsers.toString());
                // User user = dataSnapshot.getValue(User.class);
                showNextUser();
            }

            //
//                @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;


    }


    //This is how you put a matchedUser into the database
    public void onMatch(String username, String id) {
        Log.i("Called", "onMatch");
        myRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserID).child("matched_users");
        MatchedUser match = new MatchedUser(username, id);
        myRef.child(id).setValue(match);

        myRef = FirebaseDatabase.getInstance().getReference().child("users").child(id).child("matched_users");
        MatchedUser match2 = new MatchedUser(currentUserName, currentUserID);
        myRef.child(currentUserID).setValue(match2);
    }

    public void registerSwipeRight() {
        Log.i("Called", "Check");
        if(index < allUsers.size())
            FirebaseDatabase.getInstance().getReference().child("users").child(currentUserID).child("swipe_right").child(allUsers.get(index).getId()).setValue(new MatchedUser(allUsers.get(index).getFirstName(), allUsers.get(index).getId()));
        checkForMatch();
    }

    public void checkForMatch(){
        DatabaseReference checkReference = FirebaseDatabase.getInstance().getReference().child("users").child(allUsers.get(index).getId()).child("swipe_right").child(currentUserID);
        checkReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    FirebaseDatabase.getInstance().getReference().child("users").child(allUsers.get(index).getId()).child("matched_users").child(currentUser.getUid()).setValue(new MatchedUser(currentUserName, currentUserID));
                    FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()).child("matched_users").child(allUsers.get(index).getId()).setValue(new MatchedUser(allUsers.get(index).getFirstName(), allUsers.get(index).getId()));
                    showNextUser();
                } else {
                    showNextUser();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    // User user = dataSnapshot.getValue(User.class);


    public void swipeRight() {
        Log.i("Called", "Swipe Right");
        if(index >= allUsers.size()){
            userName.setText("No more users");
            description.setText("Refresh the Page to maybe find more users");
        } else {
            registerSwipeRight();
        }


        //  matchedUserIdList.add(new MatchedUser(displayedUserName, displayedUserID));
    }

    public void swipeLeft() {
        Log.i("Called", "Swipe Left");
        if(index >= allUsers.size()){
            userName.setText("No more users");
            description.setText("Refresh the Page to maybe find more users");
        } else {
           showNextUser();
        }

    }

    public void showNextUser() {
        Log.i("Called", "Show Next");

        if(index >= allUsers.size()){
            userName.setText("No more users");
            description.setText("Refresh the Page to maybe find more users");
        } else {

            userName.setText(allUsers.get(index).getFirstName());
            description.setText(allUsers.get(index).getDescription());
            index++;

            Log.i("Niggas in paris", allUsers.toString());
            Log.i("Niggas in paris", "" + index + " " + allUsers.size());
        }
//        if (index < 0 || index >= allUsers.size()) {
//            userName.setText("No more users");
//            description.setText(" ");
//            return;
//        }
//
//
//        User currUser = allUsers.get(index);
//        Log.i("Showing Names", currUser.getFirstName());
//        userName.setText(currUser.getFirstName() + " " + currUser.getLastName());
//        description.setText(currUser.getDescription());
//        index++;


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.swipeRight:
                swipeRight();
                break;
            case R.id.nopeButton:
                swipeLeft();
                break;

            default:
                break;
        }
    }
/*
public void setImage(){
    storageReference = FirebaseStorage.getInstance().getReference().child("users").child(displayedUserID).child("Image");
        Glide.with(this).using(new FirebaseImageLoader()).load(storageReference).into(image );
    }
    */
}
