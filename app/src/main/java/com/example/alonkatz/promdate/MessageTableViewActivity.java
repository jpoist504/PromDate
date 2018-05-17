package com.example.alonkatz.promdate;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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

import org.w3c.dom.Text;

/**
 *
 * @author Justin
 * represents the view that contains the list of users a user cna message
 *
 */
public class MessageTableViewActivity extends AppCompatActivity {

    ListView tableOfMessages;
    FirebaseUser currentUser;
    DatabaseReference myRef;
    FirebaseListAdapter adapter;
    TextView userName;
    TextView recentMessage;
    String myUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_table_view);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            currentUser = FirebaseAuth.getInstance().getCurrentUser();
        }
        else{
            startActivity(new Intent(getApplicationContext(), LoginPage.class));
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
        tableOfMessages = (ListView)findViewById(R.id.message_table);
        adapter = new FirebaseListAdapter<MatchedUser>(this, MatchedUser.class, R.layout.table_item, myRef) {
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
                Intent intent = new Intent(MessageTableViewActivity.this, MessageListActivity.class);
                System.out.println(tableOfMessages.getItemAtPosition(position).toString());
                MatchedUser otherUser = (MatchedUser)(tableOfMessages.getItemAtPosition(position));
                intent.putExtra("UserName", otherUser.getName());
                intent.putExtra("UserID", otherUser.getUserID());
                intent.putExtra("ThisUserName", myUserName);
                startActivity(intent);
            }
        });
    }


    public void onTableButtonPressed(View view){


    }


}
