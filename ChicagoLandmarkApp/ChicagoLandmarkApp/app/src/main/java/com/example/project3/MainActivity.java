/**********************************************************
 * Program 3 - Activity-1

 Course: CS 478, Spring 2024.
 Author: Yashwi S

 Description: This file contains the MainActivity class, which serves
 as the main entry point and controller for the application 1.
 ***********************************************************/
package com.example.project3;

import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;


public class MainActivity extends AppCompatActivity {

    // Array to store landmarks and webpage
    public static String[] landmark, webPage;
    // Fragments for Chicago landmarks and web pages
    private final WebPageFragment chicagoWebPage = new WebPageFragment();
    private final LandmarkFragment chicagolandmark = new LandmarkFragment();
    FragmentManager mFragmentManager;
    // Layout containers for landmark and web page fragments
    private FrameLayout landmarkFramwork, webPageFramwork;
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private ListViewModel mModel;


    // onCreate method to initialize activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve arrays of landmarks and web pages from resources
        landmark = getResources().getStringArray(R.array.chi_landmark);
        webPage = getResources().getStringArray(R.array.web_page);

        // Initialize fragment manager and layout containers
        mFragmentManager = getSupportFragmentManager();
        landmarkFramwork = findViewById(R.id.chi_landmark_portrait);

        // Find orientation and setup layout accordingly
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            landscapeMode();
        } else {
            portraitMode();
        }

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    // Method to handle landscape mode layout
    public void landscapeMode () {
        // Set layout for landscape mode
        setContentView(R.layout.activity_main1);
        webPageFramwork = findViewById(R.id.web_page_container);

        // Replace landmark fragment in container
        final FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.chi_landmark_container, chicagolandmark);
        fragmentTransaction.commit();

        // Add listener for back stack changes
        mFragmentManager.addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setLayout();
                    }
                });

        // Setup ViewModel for communication between fragments
        mModel = new ViewModelProvider(this).get(ListViewModel.class);
        // Handle the clicks
        mModel.getSelectedItem().observe(this, item -> {
            // Check if web page fragment is already added
            if (!chicagoWebPage.isAdded()) {
                // Add the web page fragment
                FragmentTransaction fragmentTransaction2 = mFragmentManager.beginTransaction();
                fragmentTransaction2.add(R.id.web_page_container, chicagoWebPage);
                fragmentTransaction2.addToBackStack(null);
                fragmentTransaction2.commit();
                mFragmentManager.executePendingTransactions();
            }
        });
        setLayout();        // Set initial layout
    }


    // Method to handle portrait mode layout
    public void portraitMode () {
        // Replace layout for portrait mode
        final FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.chi_landmark_portrait, chicagolandmark);
        fragmentTransaction.commit();

        // Setup ViewModel for communication between fragments
        mModel = new ViewModelProvider(this).get(ListViewModel.class);
        // Handle the clicks
        mModel.getSelectedItem().observe(this, item -> {
            // Replace landmark fragment with web page fragment
            FragmentTransaction fragmentTransaction2 = mFragmentManager.beginTransaction();
            fragmentTransaction2.replace(R.id.chi_landmark_portrait, chicagoWebPage);
            fragmentTransaction2.addToBackStack(null);
            fragmentTransaction2.commit();
        });
    }


    // Method to set layout parameters based on web page fragment
    private void setLayout() {
        if (!chicagoWebPage.isAdded()) {
            // Full fragment layout when webPage is not inserted
            landmarkFramwork.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            webPageFramwork.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT));
        } else {
            // Adjust layout parameters if web page fragment is inserted
            landmarkFramwork.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 1f));
            webPageFramwork.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 2f));
        }
    }


    // Method to create options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }


    // Method to handle options menu item selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        // Option1 (Exit) exits the activity
        if (itemId == R.id.option1) {
            finish();
            return true;
        }
        // Option2 (Launch A2) start activity from another application
        if (itemId == R.id.shortcut_option2) {
            Intent myInt = new Intent("android.intent.action.MAIN");
            myInt.setComponent(new ComponentName("com.example.project3part2", "com.example.project3part2.MainActivity"));
            startActivity(myInt);
            return true;
        }
        return true;
    }
}
