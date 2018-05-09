package com.example.alonkatz.promdate;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.components.*;


public class MessageView extends AppCompatActivity {

    private static int SIGN_IN_REQUEST_CODE = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_table_view);

    }
}