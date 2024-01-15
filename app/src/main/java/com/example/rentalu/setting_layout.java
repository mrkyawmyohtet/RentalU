package com.example.rentalu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class setting_layout extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    TextView username;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_layout);

        //the same as other activities
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        //setting the toolbar as the action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //to enable the default title of toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //get the current username
        Intent i = getIntent();
        View headerView = navigationView.getHeaderView(0); //0 is index of the nav_drawer_header layout
        username = headerView.findViewById(R.id.userName);
        username.setText(i.getStringExtra("Username"));

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(setting_layout.this, profile_layout.class);
                i.putExtra("Username", username.getText().toString());
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_home) {
            Intent i = new Intent(setting_layout.this, newfeed_page.class);
            View headerView = navigationView.getHeaderView(0); //0 is index of the nav_drawer_header layout
            username = headerView.findViewById(R.id.userName);
            i.putExtra("Username", username.getText().toString());
            startActivity(i);
            finish();
        } else if (item.getItemId() == R.id.nav_setting) {
            //
        } else if (item.getItemId() == R.id.nav_about) {
            Intent i = new Intent(setting_layout.this, aboutus_layout.class);
            View headerView = navigationView.getHeaderView(0); //0 is index of the nav_drawer_header layout
            username = headerView.findViewById(R.id.userName);
            i.putExtra("Username", username.getText().toString());
            startActivity(i);
            finish();
        } else if (item.getItemId() == R.id.nav_share) {
            Toast.makeText(this, "Thanks for sharing our app!", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.nav_logout) {
            //logout confirmation
            AlertDialog.Builder builder = new AlertDialog.Builder(setting_layout.this);
            builder.setMessage("Do you really want to logout?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(setting_layout.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //
                }
            });
            AlertDialog mDialog = builder.create();
            mDialog.show();
        }
        return true;
    }
}