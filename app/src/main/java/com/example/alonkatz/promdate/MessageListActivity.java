package com.example.alonkatz.promdate;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Dictionary;
import java.util.Enumeration;

import okhttp3.internal.framed.Variant;

public class MessageListActivity extends AppCompatActivity{

    String userID = "1";
    //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String otherUserID = "2";
    DatabaseReference ref;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list_view);

    }

    public void sendButtonPressed(View view){
        sendMessage();
    }


    private void sendMessage(){
        String messageText = ((EditText)findViewById(R.id.message_text_input)).getText().toString();
        System.out.println(messageText);

        Message message = new Message(messageText, otherUserID);

        System.out.println(message);


    }

}
