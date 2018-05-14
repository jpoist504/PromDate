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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list_view);
        //TextView header = (TextView)findViewById(R.id.message_list_head);
        //header.setText(otherUserID);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                System.out.println(map);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        //Set up the Listview
//        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);
//        adapter = new FirebaseListAdapter(this, Message.class, R.layout.list_item, FirebaseDatabase.getInstance().getReference()) {
//            @Override
//            protected void populateView(View v, Object model, int position) {
//                TextView messageText, messageFromID;
//
//                messageText = (TextView)findViewById(R.id.message_text);
//                messageFromID = (TextView)findViewById(R.id.message_from_user);
//
//
//            }
//        };
//
//        listOfMessages.setAdapter(adapter);
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

}
