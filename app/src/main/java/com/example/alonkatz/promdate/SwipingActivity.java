package com.example.alonkatz.promdate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import  android.widget.TextView;
import android.widget.Button;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jgabrielfreitas.core.BlurImageView;
import com.google.firebase.database.FirebaseDatabase;

public class SwipingActivity extends AppCompatActivity {
    TextView textElement;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    int userId =  0;
    String userName;
    DatabaseReference usersRef = database.getReference().child("benTestUsers").child(""+userId).child("name");
    DatabaseReference likedUsersRef = database.getReference().child("LikedUsers");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swiping);

        textElement = (TextView) findViewById(R.id.name);
        textElement.setText("");



//        Button btn=(Button) findViewById(R.id.btnok);
//        btn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });


    }
//
//
//        //myRef.setValue("4");

    public void jpButtonPressed(View view){
        startActivity(new Intent(getApplicationContext(), MessageListActivity.class));
    }

    public void goBack(View view){
       finish();

    }
    public void swipedRight(View view){
    likedUsersRef.push().setValue(userId+"");
        showNextUser();

           }
    public void swipedLeft(View view){
    showNextUser();
    }
    public void showNextUser(){

        userId++;
        usersRef = database.getReference().child("benTestUsers").child(""+userId).child("name");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              userName = dataSnapshot.getValue(String.class);
                //do what you want with the email
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        textElement.setText( userName);


    }
}
