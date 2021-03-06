package com.example.alonkatz.promdate;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.firebase.auth.FirebaseAuth;

/**
 * @author Alon
 * Represents the way that a user's data is stored when they register
 */
public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    SVProgressHUD loadingGraphic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupUI(findViewById(R.id.registerBackdrop));
        mAuth = FirebaseAuth.getInstance();

        loadingGraphic = new SVProgressHUD(this);
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



    public void registerUser(View view){
        //loadingGraphic.show();
        final String email = ((EditText)findViewById(R.id.createEmailTextField)).getText().toString();
        String password = ((EditText)findViewById(R.id.createPasswordTextField)).getText().toString();
        String confirmPassword = ((EditText)findViewById(R.id.confirmPasswordTextField)).getText().toString();

        if(email.equals("")||password.equals("")||confirmPassword.equals("")) {
            Toast.makeText(this, "Please fill all the boxes", Toast.LENGTH_SHORT).show();
        } else if(!password.equals(confirmPassword)){
            Toast.makeText(this, "Passwords do not math!", Toast.LENGTH_LONG).show();
        } else{
            registerUserValues(email, password);
        }
    }

    public void registerUserValues(String email, String password){


        Intent registerValues = new Intent(getApplicationContext(), RegisterValues1.class);
        registerValues.putExtra("EMAIL", email);
        registerValues.putExtra("PASSWORD", password);


      //  loadingGraphic.dismiss();
       startActivity(registerValues);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putString("User", ((EditText)findViewById(R.id.createEmailTextField)).getText().toString());
        savedInstanceState.putDouble("myDouble", 1.9);
        savedInstanceState.putInt("MyInt", 1);
        savedInstanceState.putString("MyString", "Welcome back to Android");
        // etc.
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        EditText txt = (EditText)findViewById(R.id.createEmailTextField);
        txt.setText(savedInstanceState.getString("User"));
        double myDouble = savedInstanceState.getDouble("myDouble");
        int myInt = savedInstanceState.getInt("MyInt");
        String myString = savedInstanceState.getString("MyString");
    }


    public void goBack(View view){

        finish();
    }


}
