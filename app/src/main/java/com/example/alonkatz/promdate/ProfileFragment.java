package com.example.alonkatz.promdate;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;


/**
 * @author Alon
 * Represents the view fragment that allows th user to change the details of their profile
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private Button description;

    public ProfileFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth = FirebaseAuth.getInstance();
        description = (Button) v.findViewById(R.id.nameButton);
        description.setText("Description:");
        Button b = (Button) v.findViewById(R.id.signOut);
        b.setOnClickListener(this);

        Button a = (Button) v.findViewById(R.id.locationButton);
        a.setOnClickListener(this);

        Button c = (Button) v.findViewById(R.id.nameButton);
        c.setOnClickListener(this);



        return v;
    }

    public void handleLogout(){
        if(mAuth.getCurrentUser() != null){
            mAuth.signOut();

            if(mAuth.getCurrentUser() == null)
                Log.i("USER STATUS", "LOGOUT");

            startActivity(new Intent(getActivity(), LoginPage.class));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signOut:
                handleLogout();
                break;
            case R.id.nameButton:
                Intent next1 = new Intent(getActivity(), ChangeSettings.class);
                next1.putExtra("TYPE", "DESCRIPTION");
                Log.i("Here","here");
                startActivity(next1);
                break;
            case R.id.locationButton:
                Intent next2 = new Intent(getActivity(), ChangeSettings.class);
                next2.putExtra("TYPE", "LOCATION");
                startActivity(next2);
                break;

                default:
                    break;
        }
    }
}
