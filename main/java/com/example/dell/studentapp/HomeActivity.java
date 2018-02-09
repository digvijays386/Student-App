package com.example.dell.studentapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mToogle;
    Button btnCreate,btnView;
    Button login,signup;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mdrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToogle = new ActionBarDrawerToggle(this,mdrawerLayout,R.string.open,R.string.close);
        mdrawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        btnCreate = (Button)findViewById(R.id.btnCreate);
        btnView= (Button)findViewById(R.id.btnView);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,FormActivity.class));
            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,ViewListActivity.class));
            }
        });

        NavigationView nacView = (NavigationView)findViewById(R.id.navigation_view);
        nacView.setNavigationItemSelectedListener(this);


        login= (Button)findViewById(R.id.btnLogin);
        signup= (Button)findViewById(R.id.btnSignup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,SignUpActivity.class));
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToogle.onOptionsItemSelected(item))
        {return true;}
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_add)
            startActivity(new Intent(this,FormActivity.class));
        if (id == R.id.nav_view)
            startActivity(new Intent(this,ViewListActivity.class));

        return false;
    }
}
