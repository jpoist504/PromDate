package com.example.alonkatz.promdate;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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


public class MessageListActivity extends AppCompatActivity{


    FirebaseUser user;
    String userID;
    String otherUserID;
    Toolbar title;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    FirebaseListAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list_view);


        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            user = FirebaseAuth.getInstance().getCurrentUser();
            userID = user.getUid();
        }
        else{
            startActivity(new Intent(getApplicationContext(), LoginPage.class));
        }




        //Set up toolbar title
        title = (Toolbar)findViewById(R.id.toolbar_message_list);
        title.setTitle(getIntent().getExtras().getString("UserName"));
        otherUserID = getIntent().getExtras().getString("UserID");

        myRef = database.getReference("users").child(userID).child("messages").child("fromID").child(otherUserID);

        //Set up the Listview
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);
        adapter = new FirebaseListAdapter<Message>(this, Message.class, R.layout.list_item, myRef) {
            @Override
            protected void populateView(View v, Message model, int position) {
                TextView messageText, messageFromID;

                messageText = (TextView)v.findViewById(R.id.message_text);
                messageFromID = (TextView)v.findViewById(R.id.message_from_user);

                //This works for the instance but then after you close and come back it populates the view with the newest message
                messageText.setText(model.getMessageText());
                messageFromID.setText(model.getFromID());

            }
        };

        listOfMessages.setAdapter(adapter);
    }

    public void sendButtonPressed(View view){
        sendMessage();
    }


    private void sendMessage(){
        EditText e = (EditText)findViewById(R.id.message_text_input);
        String messageText = e.getText().toString();

        Message message = new Message(messageText, otherUserID, getIntent().getExtras().get("ThisUserName").toString());



        myRef = database.getReference("users").child(userID).child("messages").child("fromID").child(otherUserID);
        DatabaseReference messageID = myRef.push();
        messageID.setValue(message);

        myRef = database.getReference("users").child(otherUserID).child("messages").child("fromID").child(otherUserID);
        messageID = myRef.push();
        messageID.setValue(message);


        e.setText("");
    }



}
