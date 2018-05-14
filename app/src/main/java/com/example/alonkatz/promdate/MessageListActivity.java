package com.example.alonkatz.promdate;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Map;

import okhttp3.internal.framed.Variant;

public class MessageListActivity extends AppCompatActivity{

    String userID = "justin";
    //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String otherUserID = "alon";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users").child(userID).child("messages");

    FirebaseListAdapter adapter;

    Message newMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list_view);
        //TextView header = (TextView)findViewById(R.id.message_list_head);
        //header.setText(otherUserID);

        // Read from the database


        myRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                newMessage = dataSnapshot.getValue(Message.class);
                System.out.println("child added");
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //Set up the Listview
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);
        adapter = new FirebaseListAdapter<Message>(this, Message.class, R.layout.list_item, myRef) {
            @Override
            protected void populateView(View v, Message model, int position) {
                TextView messageText, messageFromID;

                messageText = (TextView)v.findViewById(R.id.message_text);
                messageFromID = (TextView)v.findViewById(R.id.message_from_user);

                if(newMessage != null) {
                    if (messageText.getText() == "")
                        messageText.setText(newMessage.getMessageText());
                    if (messageFromID.getText() == "")
                        messageFromID.setText(newMessage.getFromID());
                }
                newMessage = null;
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
        System.out.println(messageText);

        Message message = new Message(messageText, otherUserID, userID);



        myRef = database.getReference("users").child(userID).child("messages");
        DatabaseReference messageID = myRef.push();
        messageID.setValue(message);

        myRef = database.getReference("users").child(otherUserID).child("messages");
        messageID = myRef.push();
        messageID.setValue(message);

        e.setText("");
    }

    public void updateMessage(TextView messageText, TextView messageFromID, Message newMessage){
        messageText.setText(newMessage.getMessageText());
        messageFromID.setText(newMessage.getFromID());
    }


}
