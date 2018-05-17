package com.example.alonkatz.promdate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private ProfileFragment profileFragment;
    private SwipingFragment swipingFragment;
    private MatchesFragment matchesFragment;
    private int currentFrag;

    private GestureDetectorCompat gestureObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        profileFragment = new ProfileFragment();
        swipingFragment = new SwipingFragment();
        matchesFragment = new MatchesFragment();

        gestureObject = new GestureDetectorCompat(this, new LearnGesture());

        mMainNav.setSelectedItemId(R.id.nav_main);
        mMainNav.getSelectedItemId();
        currentFrag = 1;
       // Log.i("ID SHIT", mMainNav.getSelectedItemId() + " " + R.id.nav_main);
        setFragment(profileFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.nav_profile:
                        //mMainNav.setItemBackgroundResource(R.color.whiteColor);
                        setFragment(profileFragment);
                        currentFrag = 0;
                        return true;
                    case R.id.nav_main:
                        //mMainNav.setItemBackgroundResource(R.color.darkTransparentColor);
                        setFragment(swipingFragment);
                        currentFrag = 1;
                        return true;
                    case R.id.nav_matches:
                        //mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(matchesFragment);
                        currentFrag = 2;
                        return true;
                        default:
                            return false;


                }

            }


        });

    }

    public boolean onTouchEvent(MotionEvent event){
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

           if(e2.getX() > e1.getX()){
               if(currentFrag == 1){
                       setFragment(profileFragment);
                   currentFrag = 0;
                   mMainNav.setSelectedItemId(R.id.nav_profile);
               }
               if(currentFrag == 2){
                   setFragment(swipingFragment);
                   currentFrag = 1;
                   mMainNav.setSelectedItemId(R.id.nav_main);
               }
           } else if (e2.getX() < e1.getX()){
               if(currentFrag == 1){
                   setFragment(matchesFragment);
                   currentFrag = 2;
                   mMainNav.setSelectedItemId(R.id.nav_matches);
               }
               if(currentFrag == 0){
                   setFragment(swipingFragment);
                   currentFrag = 1;
                   mMainNav.setSelectedItemId(R.id.nav_main);
               }
           }

            return true;
        }
    }


    private void setFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

}

