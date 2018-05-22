package com.example.alonkatz.promdate;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterValues1 extends AppCompatActivity {

    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_values1);
        setupUI(findViewById(R.id.registerValuesLayout));

        email = getIntent().getStringExtra("EMAIL");
        password = getIntent().getStringExtra("PASSWORD");
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(), 0);
    }

    /**
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

    public void registerUsers(View view){
        //loadingGraphic.show();
        final String location = ((EditText)findViewById(R.id.location)).getText().toString();
        String description = ((EditText)findViewById(R.id.descriptionEditText)).getText().toString();
        //Image img = ((ImageView)findViewById(R.id.userImagePicker)).setImageDrawable();

        if(location.equals("")||description.equals("")) {
            Toast.makeText(this, "Please fill all the boxes", Toast.LENGTH_SHORT).show();
        } else{
            registerUserValues(location, description);
        }
    }

    public void registerUserValues(String location, String description){


        Intent registerValues = new Intent(getApplicationContext(), RegisterValues2.class);
        registerValues.putExtra("EMAIL", email);
        registerValues.putExtra("PASSWORD", password);
        registerValues.putExtra("LOCATION", location);
        registerValues.putExtra("DESCRIPTION", description);

        startActivity(registerValues);
    }
}
