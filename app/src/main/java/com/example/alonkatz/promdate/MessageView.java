package com.example.alonkatz.promdate;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.components.*;
import com.google.firebase.auth.*;

/**
 * @author Justin
 * Represents the screent hat holds all of the messages a specific user can view and send
 */
public class MessageView extends AppCompatActivity {

    private static int SIGN_IN_REQUEST_CODE = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_table_view);

    }


}