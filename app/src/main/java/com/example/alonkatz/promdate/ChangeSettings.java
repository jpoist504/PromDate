package com.example.alonkatz.promdate;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeSettings extends AppCompatActivity {

    String type;
    FirebaseAuth mAuth;

    EditText editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_settings);

        setupUI(findViewById(R.id.backdrop2));

        mAuth = FirebaseAuth.getInstance();

        editor = (EditText)findViewById(R.id.editor);

        type = getIntent().getStringExtra("TYPE");


        if (type.equals("DESCRIPTION")){
            editor.setHint("Description");
        } else {
            editor.setHint("Location");
        }


    }

    public void donePressed(View view){
        if (editor.getText().equals("")){
            finish();
        } else {
            if (type.equals("DESCRIPTION")){
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                database.getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("description").setValue(editor.getText());
                finish();
            } else {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                database.getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("location").setValue(editor.getText());
                finish();
            }
        }
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(), 0);
    }


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





}
