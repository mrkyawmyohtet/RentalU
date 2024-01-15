package com.example.rentalu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class newfeed_page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecViewAdapter.SelectListener {

    RecyclerView recView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    TextView username;
    NavigationView navigationView;
    Button new_upload;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_home) {
            //
        }
        else if(item.getItemId() == R.id.nav_setting){
            Intent i = new Intent(newfeed_page.this, setting_layout.class);
            View headerView = navigationView.getHeaderView(0); //0 is index of the nav_drawer_header layout
            username = headerView.findViewById(R.id.userName);
            i.putExtra("Username", username.getText().toString());
            startActivity(i);
            finish();
        }
        else if(item.getItemId() == R.id.nav_about){
            Intent i = new Intent(newfeed_page.this, aboutus_layout.class);
            View headerView = navigationView.getHeaderView(0); //0 is index of the nav_drawer_header layout
            username = headerView.findViewById(R.id.userName);
            i.putExtra("Username", username.getText().toString());
            startActivity(i);
            finish();
        }
        else if(item.getItemId() == R.id.nav_share){
            Toast.makeText(this, "Thanks for sharing our app!", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId() == R.id.nav_logout){
            //logout confirmation
            AlertDialog.Builder builder = new AlertDialog.Builder(newfeed_page.this);
            builder.setMessage("Do you really want to logout?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(newfeed_page.this, MainActivity.class);
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

    //close drawer on back pressed
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfeed_page);
        
        recView = (RecyclerView) findViewById(R.id.items);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        new_upload = (Button) findViewById(R.id.new_upload);

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

        //to view profile
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Go to the profile page
                Intent i = new Intent(newfeed_page.this, profile_layout.class);
                i.putExtra("Username", username.getText().toString());
                startActivity(i);
                finish();
            }
        });

        //get the username intent from the inputfields layout
        Intent u = getIntent();
        username.setText(u.getStringExtra("Username"));

        //to display properties in the newfeed page with recycle view
        displayProperty();

        new_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(newfeed_page.this, inputfields_page.class);
                i.putExtra("Username", username.getText().toString());
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onItemClicked(PropertyModel propertyModel) {
//        Toast.makeText(this, "Ref_List_Num" + propertyModel.getRef_list_num() + "User_id" + propertyModel.getUser_id(), Toast.LENGTH_SHORT).show();
        DBHelper dbHelper = new DBHelper(newfeed_page.this, "user_table", null, 1);
        String Post_username = dbHelper.getUsername(propertyModel.getUser_id());
        //to inform if this is own post or not
        if(Post_username.equals(username.getText().toString())){
            int ref_list_num = propertyModel.getRef_list_num(); //get the id of specific property
            Intent i = new Intent(newfeed_page.this, propertyview_page.class);
            i.putExtra("Ref_Num", ref_list_num);
            i.putExtra("isOwn", true);
            i.putExtra("Username", username.getText().toString()); //to stay in the current user account
            startActivity(i);
            finish();
        }
        else{
            int ref_list_num = propertyModel.getRef_list_num(); //get the id of specific property
            Intent i = new Intent(newfeed_page.this, propertyview_page.class);
            i.putExtra("Ref_Num", ref_list_num);
            i.putExtra("Username", username.getText().toString()); //to stay in the current user account
            startActivity(i);
            finish();
        }

//        if(Post_username.equals(username.getText().toString())){
//
////            AlertDialog.Builder builder = new AlertDialog.Builder(newfeed_page.this);
////            builder.setMessage("What do you want to do with your post?").setPositiveButton("Update", new DialogInterface.OnClickListener() {
////                @Override
////                public void onClick(DialogInterface dialog, int which) {
////                    //TODO:update the property post
////                    int ref_list_num = propertyModel.getRef_list_num(); //get the id of specific property
////
////                }
////            }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
////                @Override
////                public void onClick(DialogInterface dialog, int which) {
////                    AlertDialog.Builder builderDel = new AlertDialog.Builder(newfeed_page.this);
////                    builderDel.setMessage("Do you really want to delete your post?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
////                        @Override
////                        public void onClick(DialogInterface dialog, int which) {
////                            dbHelper.deleteProperty(String.valueOf(propertyModel.getRef_list_num()));
////                            displayProperty(); //refresh the recycle view in newfeed page
////                            Toast.makeText(newfeed_page.this, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
////                        }
////                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
////                        @Override
////                        public void onClick(DialogInterface dialog, int which) {
////                            //
////                        }
////                    });
////                    AlertDialog alertDialog = builderDel.create();
////                    alertDialog.show();
////                }
////            });
////            AlertDialog dialog = builder.create();
////            dialog.show();
//        }
//        else{
//            Toast.makeText(this, "This is not your post", Toast.LENGTH_SHORT).show();
//        }
    }
    
    public void displayProperty(){
        //creating an recyclerview adapter object
        RecViewAdapter adapter = new RecViewAdapter(newfeed_page.this, (RecViewAdapter.SelectListener) this);
        recView.setLayoutManager(new LinearLayoutManager(newfeed_page.this));
        
        //creating an arraylist to store the houses' information
        DBHelper dbHelper = new DBHelper(newfeed_page.this, "property_table", null, 1);
        ArrayList<PropertyModel> houses = dbHelper.readProperty();

        adapter.setHouses(houses);
        recView.setAdapter(adapter);
    }
}