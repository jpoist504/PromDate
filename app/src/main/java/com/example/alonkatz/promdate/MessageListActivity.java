package com.example.alonkatz.promdate;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MessageListActivity extends AppCompatActivity{


    FirebaseUser user;
    String userID;
    String otherUserID = "GK7wDBqKwyYC2Df6E2WVwyFW7vF2";

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

        myRef = database.getReference("users").child(userID).child("messages");

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

        Message message = new Message(messageText, otherUserID, user.getDisplayName());



        myRef = database.getReference("users").child(userID).child("messages");
        DatabaseReference messageID = myRef.push();
        messageID.setValue(message);

        myRef = database.getReference("users").child(otherUserID).child("messages");
        messageID = myRef.push();
        messageID.setValue(message);

        e.setText("");
    }



}
