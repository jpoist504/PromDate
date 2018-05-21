package com.example.alonkatz.promdate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class RegisterValues2 extends AppCompatActivity {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String birthday;
    private String location;
    private String description;

    private TextView mDisplayDate;
    private int age;
    private boolean ageSelected;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private boolean isMale = false;
    private boolean genderSelected = false;
    private FirebaseAuth mAuth;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_values);

        setupUI(findViewById(R.id.registerValuesBackdrop));
        setupUI(findViewById(R.id.birthDate));
        mAuth = FirebaseAuth.getInstance();

        EditText targetEditText = (EditText) findViewById(R.id.lastName);
        targetEditText.setOnEditorActionListener(new DoneOnEditorActionListener());

        setToggleListeners();
        setUpDatePicker();
        ageSelected = false;

        email = getIntent().getStringExtra("EMAIL");
        password = getIntent().getStringExtra("PASSWORD");
        location = getIntent().getStringExtra("LOCATION");
        description = getIntent().getStringExtra("DESCRIPTION");
        Log.i("email", email + " " + password + " " + location + " " + description + " ");


    }

    public void goBack(View view){

        finish();
    }

    public void setToggleListeners() {
        final ToggleButton maleToggle = (ToggleButton) findViewById(R.id.maleToggle);
        final ToggleButton femaleToggle = (ToggleButton) findViewById(R.id.femaleToggle);

        maleToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    femaleToggle.setChecked(false);
                    isMale = true;
                    genderSelected = true;
                    Log.i("Toggles", "Male is checked");
                } else {
                    isMale = false;
                    if (!femaleToggle.isChecked()) {
                        genderSelected = false;
                    } else {
                        genderSelected = true;
                    }
                    Log.i("Toggles", "Male is unchecked");
                }
            }
        });

        femaleToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isMale = false;
                    maleToggle.setChecked(false);
                    genderSelected = true;
                    Log.i("Toggles", "Female is checked");
                } else {
                    isMale = true;
                    if (!maleToggle.isChecked()) {
                        genderSelected = false;
                    } else {
                        genderSelected = true;
                    }
                    Log.i("Toggles", "Female is unchecked");
                }
            }
        });


    }

    public void registerUser(View view) {
        firstName = ((EditText) findViewById(R.id.firstName)).getText().toString();
        lastName = ((EditText) findViewById(R.id.lastName)).getText().toString();
        birthday = ((TextView) findViewById(R.id.birthDate)).getText().toString();

        if (firstName.equals("") || lastName.equals("") || birthday.equals("") || !genderSelected || !ageSelected) {
            Toast.makeText(this, "Please fill all the boxes", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Here", Toast.LENGTH_SHORT).show();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Email Creation", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                registerUserValues(user);


                            } else {
                                //loadingGraphic.dismiss();
                                // If sign in fails, display a message to the user.
                                Log.w("Email Creation", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterValues2.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });

        }
    }

    public void registerUserValues(FirebaseUser user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("users").child(user.getUid());
        Log.i("email2", email + " " + password + " " + location + " " + description + " " + firstName);
        myRef.setValue(new User(user.getUid(), firstName, lastName, email, isMale, birthday, description, location, age));


        //        myRef.child("firstName").setValue(firstName);
//        myRef.child("lastName").setValue(lastName);
//        myRef.child("email").setValue(email);
//        myRef.child("isMale").setValue(isMale);

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

//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                User user = dataSnapshot.getValue(User.class);
//                Log.i("User", user.getEmail());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void setUpDatePicker() {
        mDisplayDate = (TextView) findViewById(R.id.birthDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RegisterValues2.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("The date", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;

                Calendar c = Calendar.getInstance();
                int currentYear = c.get(Calendar.YEAR);
                int currentMonth = c.get(Calendar.MONTH);
                int currentDay = c.get(Calendar.DAY_OF_MONTH);

                age = currentYear - year;

                if(currentMonth < month){
                    age--;
                } else if (currentMonth == month){
                    if(currentDay < day){
                        age--;
                    }
                }

                if (age < 14){
                    ageSelected = false;
                    Toast.makeText(RegisterValues2.this, "You must be older than 14 years old", Toast.LENGTH_SHORT).show();
                } else {
                    mDisplayDate.setText(date);
                    ageSelected = true;
                }



            }
        };

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


}

class DoneOnEditorActionListener extends AppCompatActivity implements TextView.OnEditorActionListener {
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            return true;
        }
        return false;
    }
}
