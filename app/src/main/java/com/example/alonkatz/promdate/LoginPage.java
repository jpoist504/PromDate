package com.example.alonkatz.promdate;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jgabrielfreitas.core.BlurImageView;


/**
 * @author Alon
 * This class represents the log in page that users use to log into their accounts for our app
 */
public class LoginPage extends AppCompatActivity {

    BlurImageView ferrari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        ferrari = (BlurImageView) findViewById(R.id.background);
        ferrari.setBlur(2);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message1243");




        myRef.setValue("Hello, srf!");
    }

    public void clickFunction(View view){
        Toast.makeText(LoginPage.this, "hello", Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(getApplicationContext(), SwipingActivity.class));
    }
}
