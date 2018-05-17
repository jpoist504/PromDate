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


/**
 * A simple {@link Fragment} subclass.
 */
public class MatchesFragment extends Fragment {

    ListView tableOfMessages;
    FirebaseUser currentUser;
    DatabaseReference myRef;
    FirebaseListAdapter adapter;
    TextView userName;
    TextView recentMessage;
    String myUserName;

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
            //startActivity(new Intent(this, LoginPage.class));
        }

        myRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()).child("firstName");

        myRef.addValueEventListener(new ValueEventListener() {
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


        myRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()).child("matched_users");


        //Set up the Listview
        tableOfMessages = (ListView)view.findViewById(R.id.message_table_fragment);
        adapter = new FirebaseListAdapter<MatchedUser>(getActivity(), MatchedUser.class, R.layout.table_item, myRef) {
            @Override
            protected void populateView(View v, MatchedUser model, int position) {

                userName = (TextView)v.findViewById(R.id.user_name);
                recentMessage = (TextView)v.findViewById(R.id.recent_message);

                userName.setText(model.getName());
                recentMessage.setText(model.getUserID());

            }
        };

        tableOfMessages.setAdapter(adapter);


        tableOfMessages.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MessageListActivity.class);
                System.out.println(tableOfMessages.getItemAtPosition(position).toString());
                MatchedUser otherUser = (MatchedUser)(tableOfMessages.getItemAtPosition(position));
                intent.putExtra("UserName", otherUser.getName());
                intent.putExtra("UserID", otherUser.getUserID());
                intent.putExtra("ThisUserName", myUserName);
                startActivity(intent);
            }
        });


        return view;

    }


}
