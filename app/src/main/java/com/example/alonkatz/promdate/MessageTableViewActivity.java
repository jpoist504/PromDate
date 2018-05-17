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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class MessageTableViewActivity extends AppCompatActivity {

    ListView tableOfMessages;
    FirebaseUser currentUser;
    DatabaseReference myRef;
    FirebaseListAdapter adapter;
    TextView userName;
    TextView recentMessage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_table_view);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            currentUser = FirebaseAuth.getInstance().getCurrentUser();
        }
        else{
            startActivity(new Intent(getApplicationContext(), LoginPage.class));
        }


        myRef = FirebaseDatabase.getInstance().getReference().child("user").child(currentUser.getUid()).child("matched_users").child("m5ukzBNk2qQhrn6OBO424qkeeXX2").child("name");

        System.out.println("PLACE 1");
        //Set up the Listview
        tableOfMessages = (ListView)findViewById(R.id.message_table);
        adapter = new FirebaseListAdapter<String>(this, String.class, R.layout.table_item, myRef) {
            @Override
            protected void populateView(View v, String model, int position) {

                userName = (TextView)v.findViewById(R.id.user_name);
                recentMessage = (TextView)v.findViewById(R.id.recent_message);

                System.out.println("PLACE 2");
                userName.setText("hello");

            }
        };

        tableOfMessages.setAdapter(adapter);

        tableOfMessages.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MessageTableViewActivity.this, MessageListActivity.class);
                intent.putExtra("OtherUserID", tableOfMessages.getItemIdAtPosition(position));
                startActivity(intent);
            }
        });
    }


    public void onTableButtonPressed(View view){


    }


}
