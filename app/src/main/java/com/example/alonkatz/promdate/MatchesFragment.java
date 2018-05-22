package com.example.alonkatz.promdate;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * @author Justin
 * This class represents the back end of the matches portion of the screen (The screent that displays all of the messages a user has.
 */
public class MatchesFragment extends Fragment {

    private ListView tableOfMessages;
    private FirebaseUser currentUser;
    private DatabaseReference myRef, lastRef, firstRef, messageRef;
    private FirebaseListAdapter adapter;
    private TextView userName, recentMessage, hiddenMessage;
    private String myUserName;
    private String message;
    private ArrayList<String> allMessages = new ArrayList<String>();
    private boolean hasMatches = false;

    public MatchesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_matches, container, false);


            if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                currentUser = FirebaseAuth.getInstance().getCurrentUser();
            }
            else{
            }

            firstRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()).child("firstName");

            firstRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String value = dataSnapshot.getValue(String.class);
                    myUserName = value;
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value

                }
            });

            lastRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()).child("lastName");

            lastRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String value = dataSnapshot.getValue(String.class);
                    myUserName = myUserName + " " + value;
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value

                }
            });



            myRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()).child("matched_users");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null)
                        hasMatches = true;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            if(hasMatches) {
                //Set up the Listview
                tableOfMessages = (ListView) view.findViewById(R.id.message_table_fragment);
                adapter = new FirebaseListAdapter<MatchedUser>(getActivity(), MatchedUser.class, R.layout.table_item, myRef) {
                    @Override
                    protected void populateView(View v, MatchedUser model, int position) {

                        userName = (TextView) v.findViewById(R.id.user_name);
                        recentMessage = (TextView) v.findViewById(R.id.recent_message);

                        //Compute the name of the user from the form that it is stored in
                        String otherName = model.getName();
                        for (int i = 1; i < otherName.length(); i++) {
                            if (otherName.charAt(i) < 'a') {
                                otherName = otherName.substring(0, i) + " " + otherName.substring(i);
                                i++;
                            }

                        }

                        //Grab the most recent message from the database
                        messageRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()).child("messages").child("fromID").child(model.getUserID());


                        messageRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    allMessages.add(data.getValue(Message.class).getMessageText());
                                    for (int i = 0; i < allMessages.size(); i++)
                                    System.out.println("PLACE_ " + i + allMessages.get(i));
                                }
                                if (allMessages.size() != 0)
                                    message = allMessages.get(allMessages.size() - 1);
                                else
                                    message = "No messages yet, say hello to ";
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                        if (message == "No messages yet, say hello to ")
                            message += otherName;

                        userName.setText(otherName);
                        recentMessage.setText(message);

                        System.out.println(userName);
                        System.out.println("this is the message " + message);

                    }
                };

                tableOfMessages.setAdapter(adapter);


                tableOfMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), MessageListActivity.class);
                        System.out.println(tableOfMessages.getItemAtPosition(position).toString());
                        MatchedUser otherUser = (MatchedUser) (tableOfMessages.getItemAtPosition(position));
                        intent.putExtra("UserName", otherUser.getName());
                        intent.putExtra("UserID", otherUser.getUserID());
                        intent.putExtra("ThisUserName", myUserName);
                        startActivity(intent);
                    }
                });

            }
            else {
                hiddenMessage = (TextView)view.findViewById(R.id.hidden_message);
                hiddenMessage.setVisibility(View.VISIBLE);
            }
        return view;

    }


}
