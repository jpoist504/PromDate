package com.example.alonkatz.promdate;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import android.widget.Toast;


import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jgabrielfreitas.core.BlurImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Alon
 * This class represents the log in page that users use to log into their accounts for our app
 */
public class LoginPage extends AppCompatActivity {

    BlurImageView ferrari;
    SVProgressHUD loadingGraphic;
    private FirebaseAuth mAuth;

    //onResume()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);



        setupUI(findViewById(R.id.backdrop));
        mAuth = FirebaseAuth.getInstance();

        loadingGraphic = new SVProgressHUD(this);

        if(mAuth.getCurrentUser()!= null){
//            final List<String> userIdList = new ArrayList();
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference myRef = database.getReference().child("users");
//            myRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    if(dataSnapshot==null)return;
//                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                        userIdList.add(postSnapshot.getKey());
//                    }
//
//                   // User user = dataSnapshot.getValue(User.class);
//                    Log.i("User", "" + userIdList.toString());
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        ferrari = (BlurImageView) findViewById(R.id.background);
        ferrari.setBlur(2);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);


//        String userId = "" + 1;
//
//        DatabaseReference myRef = database.getReference().child("users").child(userId).child("name");
//
//
//        //myRef.setValue("4");
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Log.i("Info", dataSnapshot.getValue().toString());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

    }

    public void registerPressed(View view){
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }


    public void benButtonPressed(View view){
        startActivity(new Intent(getApplicationContext(), SwipingActivity.class));
        //finish();


    }

    public void jpButtonPressed(View view){

        startActivity(new Intent(getApplicationContext(), MessageListActivity.class));
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(), 0);
    }

    /**
     *
     * @param view the current view that were are in
     */
    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard();
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public void loginUser(View view){
        loadingGraphic.show();
        String email = ((EditText)findViewById(R.id.emailTextField)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordTextField)).getText().toString();

        if (email.equals("") || password.equals("")){
            loadingGraphic.dismiss();
            Toast.makeText(this, "Please fill in all the boxes", Toast.LENGTH_LONG).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Success", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                loadingGraphic.dismiss();
                                ((EditText)findViewById(R.id.emailTextField)).setText("");
                                ((EditText)findViewById(R.id.passwordTextField)).setText("");
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                                startActivity(new Intent(getApplicationContext(), SwipingActivity.class));
//                                startActivity(new Intent(getApplicationContext(), MessageTableViewActivity.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Failed", "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginPage.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                loadingGraphic.dismiss();
                                ((EditText)findViewById(R.id.passwordTextField)).setText("");

                            }

                            // ...
                        }
                    });
        }

    }


}
